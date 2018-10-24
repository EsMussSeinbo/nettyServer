package com.hisense.nettyServer.support.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpDownLoadUtil {
	public static void main(String[] args) {
		String ftpHost = "127.0.0.1";
		String ftpUserName = "test";
		String ftpPassword = "test";
		int ftpPort = 21;
		String ftpPath = "lstest/";
		String localPath = "F:/destination";
		String fileName = "AV_3014-722012_2_20180916203833247_B.AVI";
		FtpDownLoadUtil.downloadFtpFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, localPath, fileName);
	}

	/**
	 * ��ȡFTPClient����
	 * 
	 * @param ftpHost
	 *            FTP����������
	 * @param ftpPassword
	 *            FTP ��¼����
	 * @param ftpUserName
	 *            FTP��¼�û���
	 * @param ftpPort
	 *            FTP�˿� Ĭ��Ϊ21
	 * @return
	 */
	public static FTPClient getFTPClient(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort) {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ftpHost, ftpPort);// ����FTP������
			ftpClient.login(ftpUserName, ftpPassword);// ��½FTP������
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				// logger.info("δ���ӵ�FTP���û������������");
				ftpClient.disconnect();
			} else {
				// logger.info("FTP���ӳɹ���");
			}
		} catch (SocketException e) {
			e.printStackTrace();
			// logger.info("FTP��IP��ַ���ܴ�������ȷ���á�");
		} catch (IOException e) {
			e.printStackTrace();
			// logger.info("FTP�Ķ˿ڴ���,����ȷ���á�");
		}
		return ftpClient;
	}

	/*
	 * ��FTP�����������ļ�
	 * 
	 * @param ftpHost FTP IP��ַ
	 * 
	 * @param ftpUserName FTP �û���
	 * 
	 * @param ftpPassword FTP�û�������
	 * 
	 * @param ftpPort FTP�˿�
	 * 
	 * @param ftpPath FTP���������ļ�����·�� ��ʽ�� ftptest/aa
	 * 
	 * @param localPath ���ص����ص�λ�� ��ʽ��H:/download
	 * 
	 * @param fileName �ļ�����
	 */
	public static void downloadFtpFile(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort,
			String ftpPath, String localPath, String fileName) {

		FTPClient ftpClient = null;
		OutputStream os = null;
		try {
			ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
			ftpClient.setControlEncoding("UTF-8"); // ����֧��
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(ftpPath);

			File localFile = new File(localPath + File.separatorChar + fileName);
			os = new FileOutputStream(localFile);
			ftpClient.retrieveFile(new String(fileName.getBytes("GBK"), "ISO-8859-1"), os);

		} catch (FileNotFoundException e) {
			// logger.error("û���ҵ�" + ftpPath + "�ļ�");
			e.printStackTrace();
		} catch (SocketException e) {
			// logger.error("����FTPʧ��.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			// logger.error("�ļ���ȡ����");
		} finally {
			try {
				if (os != null)
					os.close();
				if (ftpClient != null)
					ftpClient.logout();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}