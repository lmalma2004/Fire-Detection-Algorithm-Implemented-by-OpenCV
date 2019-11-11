#include "stdafx.h"
#include "ImageChannel.h"
ImageChannel::ImageChannel(double rows, double cols, double type) {
	RGB_image = Mat(rows, cols,type);
	HSV_image = Mat(rows, cols,type);
	YCrCb_image = Mat(rows, cols, type);
}
ImageChannel::ImageChannel() {

}
ImageChannel::~ImageChannel() {

}


void ImageChannel:: Make_HSV(Mat Image) {
	cvtColor(Image, HSV_image, COLOR_BGR2HSV);
	split(Image, HSVPlanes); //hsvä�� �и��Ͽ� vector<Mat> HSVplanes_1�� ����
	H = (Mat_<uchar>)HSVPlanes[0]; //����
	S = (Mat_<uchar>)HSVPlanes[1]; //ä��
	V = (Mat_<uchar>)HSVPlanes[2]; //��
}
void ImageChannel::Make_RGB(Mat Image) {
	split(Image, RGBPlanes); //rgbä�� �и��Ͽ� vector<Mat> RGBplanes_1�� ����
	B = (Mat_<uchar>)RGBPlanes[0]; //���
	G = (Mat_<uchar>)RGBPlanes[1]; //�׸�
	R = (Mat_<uchar>)RGBPlanes[2]; //����
}
void ImageChannel::Make_YCrCb(Mat Image) {
	cvtColor(Image, YCrCb_image, COLOR_BGR2YCrCb);
	split(Image, YCrCbPlanes); //YCrCbä�� �и��Ͽ� vector<Mat> YCrCbplanes_1�� ����
	Y = (Mat_<uchar>)YCrCbPlanes[0]; //
	Cr = (Mat_<uchar>)YCrCbPlanes[1]; //
	Cb = (Mat_<uchar>)YCrCbPlanes[2]; //
}