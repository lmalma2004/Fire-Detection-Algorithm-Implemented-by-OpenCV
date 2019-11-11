#pragma once
#include "stdafx.h"
#include "opencv2/opencv.hpp"

class MotionDetectionMask {
public :
	MotionDetectionMask(int rows, int cols);
	~MotionDetectionMask();

public:
	int nTh= 50;
	Size size;
	Mat GrayImage;
	Mat Final_Mask;
	Mat sumImage;
	Mat frame;
public:
	void Make_bkgImage(VideoCapture inputVideo, char* bkgImage);
	Mat Get_finalmask(Mat bkgImage, Mat frame);

};