#include "stdafx.h"
#include "MotionDetectionMask.h"

MotionDetectionMask::MotionDetectionMask(int rows, int cols) {
	size = Size(rows, cols);
	sumImage = Mat(size, CV_32F, Scalar::all(0));
}
MotionDetectionMask::~MotionDetectionMask() {

}

void MotionDetectionMask::Make_bkgImage(VideoCapture inputVideo, char* bkgImage) {
	int frameNum = 0;
	//Mat frame, grayImage;
	//Mat sumImage(size, CV_32F, Scalar::all(0));
	for (;;) {
		inputVideo >> frame;
		if (frame.empty())
			break;
		cvtColor(frame, GrayImage, COLOR_BGR2GRAY);
		accumulate(GrayImage, sumImage);

		frameNum++;
		//int ckey = waitKey(delay);
		//if (ckey == 27) break;
	}
	sumImage = sumImage / (float)frameNum;
	//divide(frameNum, sumImage, sumImage);
	imwrite(bkgImage, sumImage);
}

Mat MotionDetectionMask::Get_finalmask(Mat bkgImage, Mat frame) {
	Mat Final_Mask2;

	cvtColor(frame, GrayImage, COLOR_BGR2GRAY);
	GaussianBlur(GrayImage, GrayImage, Size(5, 5), 0.5);
	absdiff(GrayImage, bkgImage, Final_Mask2); //차영상 (움직임)
	threshold(Final_Mask2, Final_Mask2, nTh, 255, THRESH_BINARY);
	return Final_Mask2;
}