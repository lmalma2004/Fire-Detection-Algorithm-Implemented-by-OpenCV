#pragma once
#include "stdafx.h"
#include "opencv2/opencv.hpp"

using namespace cv;
using namespace std;

class FireDetectionRules {

public:
	FireDetectionRules(Mat Image);
	FireDetectionRules();
	~FireDetectionRules();

public:
	Mat_<uchar> temp;
	Mat_<uchar> temp2;
	Mat_<uchar> temp3;
	Mat_<uchar> temp4;
	Mat_<uchar> temp5;
	Mat_<uchar> temp6;
	Mat_<uchar> temp7;
	Mat_<uchar> temp8;
	Mat_<uchar> temp9;
	Mat_<uchar> temp10;


public:
	Mat_<uchar>  Rule_1(Mat_<uchar> R, Mat_<uchar> G, Mat_<uchar> B);
	Mat_<uchar>  Rule_2(Mat_<uchar> R, Mat_<uchar> G, Mat_<uchar> B);
	Mat_<uchar>  Rule_3(Mat_<uchar> Y, Mat_<uchar> Cb);
	Mat_<uchar>  Rule_4(Mat_<uchar> Cr, Mat_<uchar> Cb);
	Mat_<uchar>  Rule_5(Mat_<uchar> R, Mat_<uchar> G, Mat_<uchar> B);

};