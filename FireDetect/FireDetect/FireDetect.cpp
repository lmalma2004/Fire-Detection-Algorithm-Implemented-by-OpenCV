// please.cpp : 콘솔 응용 프로그램에 대한 진입점을 정의합니다.
//
#pragma once
#include "stdafx.h"
using namespace cv;
using namespace std;

int Check_video(VideoCapture InputVideo);
int Check_image(Mat Image);
char* bkgImage_name = "bkgImage.jpg";
void Info_video(VideoCapture Video, Size *size, int *fps, int *delay, double *type);
void fire_detection(VideoCapture InputVideo, Mat bkgImage, int delay, MotionDetectionMask MotionMask);
void Check_fire(int nCount, int Green, int Yellow);

int nCount;

int main() {

	Size size;
	int fps;
	int delay;
	double type;

	//영상 읽기 inputVideo -> 배경영상 만들기 위한, inputVideo -> 화재감지를 위한
	VideoCapture inputVideo("area6.mp4");
	Check_video(inputVideo);
	VideoCapture inputVideo2("area6.mp4");
	Check_video(inputVideo2);

	//영상 정보
	Info_video(inputVideo, &size, &fps, &delay, &type);
	//배경이미지 생성
	MotionDetectionMask MotionMask = MotionDetectionMask((int)inputVideo.get(CAP_PROP_FRAME_WIDTH), (int)inputVideo.get(CAP_PROP_FRAME_HEIGHT));
	MotionMask.Make_bkgImage(inputVideo, bkgImage_name);

	//Make_bkgImage(inputVideo, size, bkgImage_name);
	Mat bkgImage = imread(bkgImage_name, IMREAD_GRAYSCALE);
	Check_image(bkgImage);

	//화재감지
	fire_detection(inputVideo2, bkgImage, delay, MotionMask);

	return 0;
}


void Info_video(VideoCapture Video, Size *size, int *fps, int *delay, double *type) {
	*size = Size((int)Video.get(CAP_PROP_FRAME_WIDTH), (int)Video.get(CAP_PROP_FRAME_HEIGHT));
	//cout << "Size =" << *size << endl;
	*fps = (int)(Video.get(CV_CAP_PROP_FPS));
	//cout << "fps=" << *fps << endl;
	*delay = 1000 / (*fps);
	//cout << "delay=" << *delay << endl;
	*type = Video.get(CAP_PROP_FORMAT);
}

int Check_image(Mat Image) {
	if (Image.empty()) {
		return -1;
	}
}

int Check_video(VideoCapture InputVideo) {
	if (!InputVideo.isOpened()) {
		cout << "Can not open capture!!!" << endl;
		return -1;
	}
}

void fire_detection(VideoCapture InputVideo, Mat bkgImage, int delay, MotionDetectionMask MotionMask) {
	Mat frame;
	Mat MotionFinalMask;
	Mat MotionWhite;
	Mat FireDetectionVideo;

	//3채널 이미지 정보 클래스 생성
	ImageChannel Three_Image = ImageChannel(InputVideo.get(CAP_PROP_FRAME_WIDTH), InputVideo.get(CAP_PROP_FRAME_HEIGHT), InputVideo.get(CAP_PROP_FORMAT));

	for (;;) {
		InputVideo >> frame;
		if (frame.empty()) {
			printf("error2");
			break;
		}

		//RGB, YCrCb 채널 정보 생성 
		Three_Image.Make_RGB(frame);
		Three_Image.Make_YCrCb(frame);
		
		//FireColor 요소 마스크 생성
		FireDetectionMask FireMask = FireDetectionMask(Three_Image);
		//Motion 요소 마스크 생성
		MotionFinalMask = MotionMask.Get_finalmask(bkgImage, frame);
		//MotionWhite = MotionMask.Get_finalmask(bkgImage, frame);

		//FireColor + Motion And 연산 수행					

		//bitwise_and(MotionWhite, FireMask.Mask5, MotionWhite);

		bitwise_and(MotionFinalMask, FireMask.FinalMask, FireDetectionVideo);

		//bitwise_or(MotionFinalMask, MotionWhite, MotionFinalMask);
	  

		nCount = countNonZero(FireDetectionVideo);
		//cout << "nCount=" << nCount << endl;

		Check_fire(nCount, 100, 300);
		//imshow("Motion", Motion);
		//imshow("MotionWhite", MotionWhite);
		imshow("frame", frame);
		imshow("Motion", MotionFinalMask);
		imshow("Color", FireMask.FinalMask);
		imshow("Final", FireDetectionVideo);
		int ckey = waitKey(delay);
		if (ckey == 27) break;
	}
}


void Check_fire(int nCount, int Green, int Yellow) {
	if (nCount < Green) {
		cout << "green" << endl;
	}
	else if (nCount < Yellow) {
		cout << "yellow" << endl;
	}
	else {
		cout << "Red" << endl;
	}
}