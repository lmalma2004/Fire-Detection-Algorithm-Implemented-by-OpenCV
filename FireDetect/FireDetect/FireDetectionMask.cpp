#include "stdafx.h"
#include "FireDetectionMask.h"


FireDetectionMask::FireDetectionMask(ImageChannel Image) : FireDetectionRules() {
	Mask1 = Rule_1(Image.R, Image.G, Image.B);
	Mask2 = Rule_2(Image.R, Image.G, Image.B);
	Mask3 = Rule_3(Image.Y, Image.Cr);
	Mask4 = Rule_4(Image.Cr, Image.Cb);
	Mask5 = Rule_5(Image.R, Image.G, Image.B);

	bitwise_and(Mask1, Mask2, FinalMask);
	bitwise_and(FinalMask, Mask3, FinalMask);
	bitwise_and(FinalMask, Mask4, FinalMask);
	//bitwise_or(FinalMask, Mask5, FinalMask);
}

FireDetectionMask::~FireDetectionMask() {

}