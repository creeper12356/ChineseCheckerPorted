package io.github.creeper12356.utils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import io.github.creeper12356.MyGame;

public class UpdateChecker {
    private String latestVersion;
    private String downloadUrl;

    public void checkUpdate() {
        String url = "https://api.github.com/repos/creeper12356/ChineseCheckerPorted/releases/latest";
        HttpRequest httpGet = new HttpRequest(HttpMethods.GET);
        httpGet.setUrl(url);

        Gdx.net.sendHttpRequest(httpGet, new HttpResponseListener() {
            @Override
            public void handleHttpResponse(com.badlogic.gdx.Net.HttpResponse httpResponse) {
                JsonReader json = new JsonReader();
                JsonValue latestRelease = json.parse(httpResponse.getResultAsString());

                latestVersion = latestRelease.getString("tag_name");

                if (latestVersion.equals(Resource.version)) {
                    System.out.println("No updates available.");
                    return;
                }

                JsonValue assets = latestRelease.get("assets");
                for (JsonValue asset : assets) {
                    String assetName = asset.getString("name");
                    if (assetName.endsWith(Resource.language + ".jar")) {
                        downloadUrl = asset.getString("browser_download_url");
                        break;
                    }
                }

                if (downloadUrl == null) {
                    System.out.println("No download URL found.");
                    return;
                }

                System.out.println("Latest version: " + latestVersion);
                System.out.println("Download URL: " + downloadUrl);

                if (JOptionPane.showConfirmDialog(
                        null,
                        "当前版本是" + Resource.version + "，最新版本是" + latestVersion + "，是否更新？",
                        "更新提示",
                        JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
                    return;
                }
                // 下载并替换jar包
                setProxy();
                downloadFile(downloadUrl, System.getProperty("user.dir"));
                unsetProxy();
            }

            @Override
            public void failed(Throwable t) {
                t.printStackTrace();
                System.out.println("Failed to check for updates.");
            }

            @Override
            public void cancelled() {
                System.out.println("Cancelled checking for updates.");
            }
        });
    }

    public void downloadFile(String fileURL, String savePath) {
        try {
            URL url = new URL(fileURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();

            // 检查HTTP响应代码
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String disposition = httpConn.getHeaderField("Content-Disposition");
                int contentLength = httpConn.getContentLength();

                // 打印文件信息
                System.out.println("Content-Type = " + httpConn.getContentType());
                System.out.println("Content-Disposition = " + disposition);
                System.out.println("Content-Length = " + contentLength);

                // 打开输入流和输出流
                InputStream inputStream = httpConn.getInputStream();
                String oldJarPath = getCurrentJarPath();
                String saveFilePath = oldJarPath + ".new";

                try (FileOutputStream outputStream = new FileOutputStream(saveFilePath);
                        BufferedInputStream in = new BufferedInputStream(inputStream)) {

                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    long totalBytesRead = 0;
                    int percentCompleted = 0;
                    long fileSize = contentLength;

                    JProgressBar progressBar = new JProgressBar(0, 100);
                    progressBar.setValue(0);
                    progressBar.setStringPainted(true);

                    JOptionPane optionPane = new JOptionPane(progressBar, JOptionPane.INFORMATION_MESSAGE,
                            JOptionPane.DEFAULT_OPTION, null, new Object[] {}, null);
                    JDialog dialog = optionPane.createDialog("下载进度");

                    dialog.setModal(false);
                    dialog.setVisible(true);

                    while ((bytesRead = in.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                        totalBytesRead += bytesRead;
                        int percent = (int) (totalBytesRead * 100 / fileSize);
                        if (percent > percentCompleted) {
                            percentCompleted = percent;
                            System.out.print("下载进度: " + percentCompleted + "%\r");
                            progressBar.setValue(percentCompleted);
                        }
                    }
                    System.out.println("\n文件下载完成，保存到: " + saveFilePath);

                    ProcessBuilder processBuilder;
                    if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                        processBuilder = new ProcessBuilder("cmd", "/c",
                                "move " + saveFilePath + " " + oldJarPath + " && java -jar " + oldJarPath);
                    } else {
                        processBuilder = new ProcessBuilder("sh", "-c",
                                "mv " + saveFilePath + " " + oldJarPath + " && chmod +x " + oldJarPath
                                        + " && java -jar " + oldJarPath);
                    }
                    processBuilder.start();

                    System.exit(0);
                }
            } else {
                System.out.println("服务器返回非预期响应代码: " + responseCode);
            }
            httpConn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setProxy() {
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "7890");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort", "7890");
    }

    private void unsetProxy() {
        System.clearProperty("http.proxyHost");
        System.clearProperty("http.proxyPort");
        System.clearProperty("https.proxyHost");
        System.clearProperty("https.proxyPort");
    }

    /**
     * @brief 获取当前jar包路径
     * @return
     */
    private String getCurrentJarPath() {
        String path = null;
        try {
            path = new File(MyGame.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getPath();
            System.out.println(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}
