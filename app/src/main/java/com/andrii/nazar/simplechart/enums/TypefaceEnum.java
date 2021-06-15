package com.andrii.nazar.simplechart.enums;


import com.andrii.nazar.simplechart.utils.PathUtil;

public enum TypefaceEnum {
    FONT_AWESOME(0, "font_awesome.ttf"),
    ROBOTO_REGULAR(1, "roboto_regular.ttf");


	private int key;
	private String fileName;

	private TypefaceEnum(int key, String fileName) {
		this.key = key;
		this.fileName = fileName;
	}

	public String getAssetFilePath() {
		return PathUtil.buildPath(new String[]{"fonts", fileName});
	}
	
	public int getKey() {
		return key;
	}

	public static TypefaceEnum findByKey(int key) {
		switch(key) {
			case 0 : {
				return FONT_AWESOME;
			}
			default : {
				return ROBOTO_REGULAR;
			}
		}
	}
}
