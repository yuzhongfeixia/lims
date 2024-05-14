package com.gpersist.action;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.struts2.ServletActionContext;

import com.gpersist.utils.ToolUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.swetake.util.Qrcode;

public class QRCodeAction extends ActionSupport {
    private static final long serialVersionUID = 1L;

    private ByteArrayInputStream inputStream;

    public String execute() throws Exception {

        ServletActionContext.getResponse().setHeader("Pragma", "no-cache");
        ServletActionContext.getResponse().setHeader("Cache-Control", "no-cache");
        ServletActionContext.getResponse().setDateHeader("Expires", -1);

        String code = ToolUtils.Decode(ServletActionContext.getRequest().getParameter("code"));
        String width = ServletActionContext.getRequest().getParameter("width");

        Qrcode qr = new Qrcode();
        qr.setQrcodeErrorCorrect('M');
        qr.setQrcodeEncodeMode('B');
        qr.setQrcodeVersion(3);

        byte[] contentBytes = code.getBytes("utf-8");

        BufferedImage bufImg = new BufferedImage(Integer.valueOf(width), Integer.valueOf(width),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D gs = bufImg.createGraphics();

        gs.setBackground(Color.WHITE);
        gs.clearRect(0, 0, Integer.valueOf(width), Integer.valueOf(width));

        gs.setColor(Color.BLACK);
        int pixoff = 2;

        if (contentBytes.length > 0 && contentBytes.length < 120) {
            boolean[][] codeOut = qr.calQrcode(contentBytes);
            for (int i = 0; i < codeOut.length; i++) {
                for (int j = 0; j < codeOut.length; j++) {
                    if (codeOut[j][i]) {
                        gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                    }
                }
            }
        } else {
            System.err.println("QRCode content bytes length = " + contentBytes.length + " not in [ 0,120 ]. ");
        }

        gs.dispose();

        ByteArrayInputStream input = null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
            ImageIO.write(bufImg, "JPEG", imageOut);
            imageOut.close();
            input = new ByteArrayInputStream(output.toByteArray());
        } catch (Exception e) {
            System.out.println("二维码图片产生出现错误：" + e.toString());
        }

        this.setInputStream(input);

        return SUCCESS;
    }

    public void setInputStream(ByteArrayInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ByteArrayInputStream getInputStream() {
        return inputStream;
    }
}