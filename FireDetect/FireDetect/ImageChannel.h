#pragma once
#include "stdafx.h"
#include "opencv2/opencv.hpp"

using namespace cv;
using namespace std;

class ImageChannel {


public:
	ImageChannel(double rows, double cols, double type);
	ImageChannel();
	~ImageChannel();

public:
	Mat HSV_image;
	Mat RGB_image;
	Mat YCrCb_image;

	Mat_<uchar> R;
	Mat_<uchar> G;
	Mat_<uchar> B;
	Mat_<uchar> H;
	Mat_<uchar> S;
	Mat_<uchar> V;
	Mat_<uchar> Y;
	Mat_<uchar> Cr;
	Mat_<uchar> Cb;

	vector<cv::Mat> HSVPlanes;
	vector<cv::Mat> RGBPlanes;
	vector<cv::Mat> YCrCbPlanes;

public:
	void Make_RGB(cv::Mat Image);
	void Make_HSV(cv::Mat Image);
	void Make_YCrCb(cv::Mat Image);

};