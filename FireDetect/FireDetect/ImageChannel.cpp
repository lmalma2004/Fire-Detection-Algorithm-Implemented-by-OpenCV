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
	split(Image, HSVPlanes); //hsv채널 분리하여 vector<Mat> HSVplanes_1에 저장
	H = (Mat_<uchar>)HSVPlanes[0]; //색상
	S = (Mat_<uchar>)HSVPlanes[1]; //채도
	V = (Mat_<uchar>)HSVPlanes[2]; //명도
}
void ImageChannel::Make_RGB(Mat Image) {
	split(Image, RGBPlanes); //rgb채널 분리하여 vector<Mat> RGBplanes_1에 저장
	B = (Mat_<uchar>)RGBPlanes[0]; //블루
	G = (Mat_<uchar>)RGBPlanes[1]; //그린
	R = (Mat_<uchar>)RGBPlanes[2]; //레드
}
void ImageChannel::Make_YCrCb(Mat Image) {
	cvtColor(Image, YCrCb_image, COLOR_BGR2YCrCb);
	split(Image, YCrCbPlanes); //YCrCb채널 분리하여 vector<Mat> YCrCbplanes_1에 저장
	Y = (Mat_<uchar>)YCrCbPlanes[0]; //
	Cr = (Mat_<uchar>)YCrCbPlanes[1]; //
	Cb = (Mat_<uchar>)YCrCbPlanes[2]; //
}