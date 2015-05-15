package org.modelcatalogue.crf.model;

public interface MinimalItem extends HasPageNumber, HasDisplayStatus {

    String getName();
    void setName(String name);

    String getDescriptionLabel();
    void setDescriptionLabel(String descriptionLabel);

    String getLeftItemText();
    void setLeftItemText(String leftItemText);

    String getUnits();
    void setUnits(String units);

    String getRightItemText();
    void setRightItemText(String rightItemText);

    Section getSection();
    Group getGroup();

    String getHeader();
    void setHeader(String header);

    String getSubheader();
    void setSubheader(String subheader);

    Integer getColumnNumber();
    void setColumnNumber(Integer columnNumber);

    String getQuestionNumber();
    void setQuestionNumber(String questionNumber);

    ResponseType getResponseType();

    Integer getPhi();
    void setPhi(Integer phi);

    Integer getRequired();
    void setRequired(Integer required);

    ConditionalDisplay getConditionalDisplay();

    void setConditionalDisplay(ConditionalDisplay conditionalDisplay);
}
