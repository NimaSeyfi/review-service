package com.seyfi.review.exception;

import lombok.Getter;

@Getter
public enum FieldCode {
    UNKNOWN_FIELD("undefinedId",10111000),
    ID("id",10111001),
    USER_ID("userId",10111002),
    PRODUCT_ID("productId",10111003),
    IS_CUSTOMER("isCustomer",10111004),
    IS_VOTABLE("isVotable",10111005),
    IS_COMMENTABLE("isCommentable",10111006),
    IS_PUBLIC("isPublic",10111007),
    IS_VISIBLE("isVisible",10111008),
    VOTE("vote",10111009),
    CONTENT("content",10111010),
    SYNC("sync",10111011),
    SIZE("size",10111012);

    private String fieldName;
    private Integer fieldCode;

    FieldCode(String fieldName, Integer fieldCode) {
        this.fieldName = fieldName;
        this.fieldCode = fieldCode;
    }

    public static Integer get_code_by_name(String fieldName){
        for(FieldCode fieldCode : values()){
            if (fieldCode.getFieldName().equals(fieldName)){
                return fieldCode.getFieldCode();
            }
        }
        return FieldCode.UNKNOWN_FIELD.getFieldCode();
    }
}
