#pragma once
#include "stdafx.h"
#include "opencv2/opencv.hpp"
#include "FireDetectionRules.h"
#include "ImageChannel.h"

using namespace cv;
using namespace std;

class FireDetectionMask : public FireDetectionRules {
public:
	FireDetectionMask(ImageChannel Image);
	~FireDetectionMask();
public:
	Mat get_FinalMask();

public:
	Mat Mask1, Mask2, Mask3, Mask4, Mask5;
	Mat FinalMask;
};