package com.sens.pond.utils.office;

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.sl.usermodel.PictureData;
import java.awt.Color;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.SlideLayout;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFAutoShape;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlideLayout;
import org.apache.poi.xslf.usermodel.XSLFSlideMaster;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;

//================================================//
// 고객사 급한 요청사항으로 코드 퀄리티는 신경 쓰지 못습니다.   //
// 추후에 리패토링 진행 예정입니다~!!                     // 
//================================================//

public class PptCreate {

	String fileCopyPath = "/Users/sinsanghun/Downloads/2019/";
	
	
	//pptx 문서를 생성한다. 
	public XMLSlideShow newPptCreate () {

//		XMLSlideShow ppt = new XMLSlideShow(); // ppt문서를 구성하는 최상위 객체
//		ppt.setPageSize(new java.awt.Dimension(960, 720));//현재 페이지 크기 설정

		
		return new XMLSlideShow();// ppt문서를 구성하는 최상위 객체
	}
	
	//ppt 파일에 담긴 ppt 문서를 파일로 저장.
	public void savePptFile (XMLSlideShow ppt , String createFileName) throws IOException {
		FileOutputStream out = new FileOutputStream(fileCopyPath + createFileName);
		ppt.write(out);
		out.close();
	}
	
	//페이지를 생성하고 슬라이드 양식에 맞게 작업을 image, text, 도형 등을 생성하고 배치한다.
	//ppt 문서의 비율 표준은  4 : 3 이며 25.4cm : 19.05cm 입니다. 와이드스크린은 16 : 9 이며  33.867cm : 19.05cm 사이즈이며 사용자가 변경도 가능 하다 
	//WorkDomain은 한개의 슬라이더를 구성하기 위한 데이터
	//workType접수 구분의 숫자 코드 값을 한글려 변환한 
	//각 레이아웃의 위치 좌표 값과 크기는 MS사 파워 포인트로 확인 하는게 편하다. 파워포인트의 디폴드 설정이 cm 이니 pixel로 변경하세요.  
	public void addSlide(int index, XMLSlideShow ppt, WorkDomain wd, String workType) throws FileNotFoundException, IOException {

		//java.awt.Dimension은 컨테이너, 컴포넌트 의 크기를 나타낼때 사용 또는 레이아웃 .equals 함수로 크기가 같은지 비교 할수도있다.
		ppt.setPageSize(new java.awt.Dimension(960, 720)); 
		XSLFSlideMaster defaultMaster = ppt.getSlideMasters().get(0); // XSLFSlideMaster는 공통 슬라이드 요소를 지정 또는 머리글 및 바닥 글과 같이 공통 사항을 설정 할수 있다. 
		XSLFSlideLayout layout = defaultMaster.getLayout(SlideLayout.BLANK); //머리글 바닥글과 같은 공통 내역이 없어서 사용하지않음 슬라이더의 레이아웃 설정을 한다.  
		XSLFSlide slide = ppt.createSlide(layout); //XSLFSlideMaster을 통해 생성항슬라이드 레이아웃을 가지고 슬라이드를 생성한다. 
		
		// IOUtils 는 poi에서 제공 하는 클래스이려 java.lang.object 를 확장한 것이다. 파일 읽기, 쓰기 등 쉽게 파일을 컨트롤 할 수 있음. 
		byte[] pictureData = IOUtils.toByteArray(new FileInputStream(fileCopyPath + workType + wd.getFile_info_change_name()+wd.getFile_info_origin_extensions()));
		ppt.addPicture(pictureData, PictureData.PictureType.JPEG);
		
		XSLFPictureData pd = null;
		
		if(wd.getFile_info_origin_extensions().equalsIgnoreCase(".jpg") || wd.getFile_info_origin_extensions().equalsIgnoreCase(".jpeg") ) {
			// 이미지 데이터와 , 이미지 형식을 맞춰 설정
			pd = ppt.addPicture(pictureData, PictureData.PictureType.JPEG);
		}else if (wd.getFile_info_origin_extensions().equalsIgnoreCase(".png")) {
			pd = ppt.addPicture(pictureData, PictureData.PictureType.PNG);
		}else {
			pd = ppt.addPicture(pictureData, PictureData.PictureType.JPEG);
		}
		
		XSLFPictureShape picture = slide.createPicture(pd); // 이전 단계에서 준비한 이미지 파일 정보를 슬라이드에 추가한다. 
		
		int widthOffset = 0; // x좌표 
		int heightOffset = 0; // y좌표
		
		// 이미지 사이즈에 맞게 ppt문서  가운데 정렬 하여 출력하기 위해 좌표를 구하한다. 
		if( ( (960 - wd.getPpt_width()) <= 0 )  || ( (604 - wd.getPpt_heigth()) >= 0 && (960 - wd.getPpt_width()) >= 0 ) ) {
			heightOffset = (604 - wd.getPpt_heigth()) / 2;
		}else if (604 - wd.getPpt_heigth() <= 0) {
			widthOffset = (960 - wd.getPpt_width() ) / 2;
		}
		
		//이미지의 좌표 및 크기 설정
		picture.setAnchor(new Rectangle(widthOffset, heightOffset,wd.getPpt_width(), wd.getPpt_heigth()));
	
		//ppt 문서 왼쪽 하단에 사각형을 그리기
		XSLFAutoShape createAutoShape = slide.createAutoShape(); // 도형 컨테이너 생성 
		createAutoShape.setAnchor(new Rectangle(0, 604, 288, 116)); // 도형의 좌표 와 크기 설정
		createAutoShape.setFillColor(new Color(243, 112,33)); //사각형의 바탕색을 설정
	
		XSLFTextBox xb1= slide.createTextBox();
		XSLFTextParagraph fp1 = xb1.addNewTextParagraph();
		XSLFTextRun r1 = fp1.addNewTextRun();
		xb1.setAnchor(new Rectangle(9, 614, 57, 32));
		r1.setText("접수 부분");
		r1.setFontColor(Color.black);
		r1.setFontSize((double) 10);
		r1.setBold(true);
		
		
		//텍스트 박수 생성 -> 택스트 단락 생성 -> 텍스트 생성 
		XSLFTextBox xb11= slide.createTextBox(); //텍스트 박스 생성 
		XSLFTextParagraph fp11 = xb11.addNewTextParagraph(); //텍스트 단락 생성 
		XSLFTextRun r11 = fp11.addNewTextRun(); // 텍스트 생성
		xb11.setAnchor(new Rectangle(67, 614, 220, 32)); // 텍스트 박스의 좌표 및 크기 설정 
		r11.setText(workType.replace("_", " | ") + wd.getFile_info_change_name()); // 텍스트 데이터 넣기  
		r11.setFontColor(Color.black); // 텍스트 컬러 설정
		r11.setFontSize((double) 10); // 텍스트 사이즈 설정 
		r11.setBold(true); // 텍스트 볻드체 설정
		
		XSLFTextBox xb2= slide.createTextBox();
		XSLFTextParagraph fp2 = xb2.addNewTextParagraph();
		XSLFTextRun r2 = fp2.addNewTextRun();
		xb2.setAnchor(new Rectangle(9, 646, 57, 32));
		r2.setText("접수번호");
		r2.setFontSize((double) 10);
		r2.setBold(true);
		
		XSLFTextBox xb22= slide.createTextBox();
		XSLFTextParagraph fp22 = xb22.addNewTextParagraph();
		XSLFTextRun r22 = fp22.addNewTextRun();
		xb22.setAnchor(new Rectangle(67, 646, 220, 32));
		r22.setText(wd.getParticipants_registration());
		r22.setFontSize((double) 12);
		r22.setBold(true);
		
		XSLFTextBox xb3= slide.createTextBox();
		XSLFTextParagraph fp3 = xb3.addNewTextParagraph();
		XSLFTextRun r3 = fp3.addNewTextRun();
		xb3.setAnchor(new Rectangle(9, 678, 57, 32));
		r3.setText("작품번호");
		r3.setFontSize((double) 10);
		r3.setBold(true);
		
		XSLFTextBox xb33= slide.createTextBox();
		XSLFTextParagraph fp33 = xb33.addNewTextParagraph();
		XSLFTextRun r33 = fp33.addNewTextRun();
		xb33.setAnchor(new Rectangle(67, 678, 220, 32));
		r33.setText(Integer.toString(wd.getWork_id()));
		r33.setFontSize((double) 12);
		r33.setBold(true);
		
		XSLFTextBox xb4= slide.createTextBox();
		XSLFTextParagraph fp4 = xb4.addNewTextParagraph();
		XSLFTextRun r4 = fp4.addNewTextRun();
		xb4.setAnchor(new Rectangle(297, 610, 652, 22));
		r4.setText(wd.getWork_title());
		r4.setFontSize((double) 16);
		r4.setBold(true);
		
		XSLFTextBox xb5= slide.createTextBox();
		XSLFTextParagraph fp5 = xb5.addNewTextParagraph();
		XSLFTextRun r5 = fp5.addNewTextRun();
		xb5.setAnchor(new Rectangle(297, 632, 652, 83));
		r5.setText(wd.getWork_description());
		r5.setFontSize((double) 12);
	
		
	}
}