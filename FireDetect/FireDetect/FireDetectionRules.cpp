#include "stdafx.h"
#include "FireDetectionRules.h"


FireDetectionRules::FireDetectionRules(Mat Image) {
	temp = Mat_<uchar>(Image.rows, Image.cols);
	temp2 = Mat_<uchar>(Image.rows, Image.cols);
	temp3 = Mat_<uchar>(Image.rows, Image.cols);
	temp4 = Mat_<uchar>(Image.rows, Image.cols);
	temp5 = Mat_<uchar>(Image.rows, Image.cols);
	temp6 = Mat_<uchar>(Image.rows, Image.cols);
	temp7 = Mat_<uchar>(Image.rows, Image.cols);
	temp8 = Mat_<uchar>(Image.rows, Image.cols);
	temp9 = Mat_<uchar>(Image.rows, Image.cols);
	temp10 = Mat_<uchar>(Image.rows, Image.cols);

}
FireDetectionRules::FireDetectionRules() {

}
FireDetectionRules::~FireDetectionRules() {

}


Mat_<uchar> FireDetectionRules::Rule_1(Mat_<uchar> R, Mat_<uchar> G, Mat_<uchar> B) {
	subtract(R, G, temp);
	threshold(temp, temp, 1, 255, THRESH_BINARY); //nTh2 값이상의 픽셀은 전부 255
	subtract(G, B, temp2);
	threshold(temp2, temp2, 1, 255, THRESH_BINARY); //nTh2 값이상의 픽셀은 전부 255

	bitwise_and(temp, temp2, temp);
	//bitwise_not(temp, temp);
	//imshow("Rule_1", temp);
	return temp;
}

Mat_<uchar> FireDetectionRules::Rule_2(Mat_<uchar> R, Mat_<uchar> G, Mat_<uchar> B) {
	R.convertTo(temp3, CV_8U, 1.0, -190);
	threshold(temp3, temp3, 1, 255, THRESH_BINARY); //nTh2 값이상의 픽셀은 전부 255
	G.convertTo(temp4, CV_8U, 1.0, -100);
	//add(G, Scalar(-100), temp4);
	threshold(temp4, temp4, 1, 255, THRESH_BINARY); //nTh2 값이상의 픽셀은 전부 255
	B.convertTo(temp5, CV_8U, 1.0, -139);
	//add(B, Scalar(-139), temp5);
	threshold(temp5, temp5, 0, 255, THRESH_BINARY_INV); //nTh2 값이상의 픽셀은 전부 255

	bitwise_and(temp3, temp4, temp3);
	bitwise_and(temp3, temp5, temp3);
	//bitwise_not(temp3, temp3);

	//imshow("Rule_2", temp3);
	return temp3;
}
Mat_<uchar> FireDetectionRules::Rule_3(Mat_<uchar> Y, Mat_<uchar> Cb) {
	subtract(Y, Cb, temp6);
	threshold(temp6, temp6, 0, 255, THRESH_BINARY); //nTh2 값이상의 픽셀은 전부 255

	bitwise_not(temp6, temp6);


	//imshow("Rule_3", temp6);
	return temp6;

}
Mat_<uchar> FireDetectionRules::Rule_4(Mat_<uchar> Cr, Mat_<uchar> Cb) {
	subtract(Cr, Cb, temp7);
	threshold(temp7, temp7, 0, 255, THRESH_BINARY); //nTh2 값이상의 픽셀은 전부 255

	bitwise_not(temp7, temp7);

	//imshow("Rule_4", temp);
	return temp7;
}


Mat_<uchar> FireDetectionRules::Rule_5(Mat_<uchar> R, Mat_<uchar> G, Mat_<uchar> B) {
	
	threshold(R, temp8, 255, 255, THRESH_BINARY); //nTh2 값이상의 픽셀은 전부 255
	threshold(G, temp9, 255, 255, THRESH_BINARY); //nTh2 값이상의 픽셀은 전부 255
	threshold(B, temp10, 255, 255, THRESH_BINARY); //nTh2 값이상의 픽셀은 전부 255

	bitwise_and(temp8, temp9, temp8);
	bitwise_and(temp8, temp10, temp8);
	//bitwise_not(temp3, temp3);

	//imshow("Rule_5", temp8);
	return temp8;

}