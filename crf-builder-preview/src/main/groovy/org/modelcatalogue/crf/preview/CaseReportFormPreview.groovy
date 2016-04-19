package org.modelcatalogue.crf.preview

import groovy.xml.StreamingMarkupBuilder
import org.modelcatalogue.crf.model.CaseReportForm
import org.modelcatalogue.crf.model.Item
import org.modelcatalogue.crf.model.ResponseOption
import org.modelcatalogue.crf.model.ResponseType
import org.modelcatalogue.crf.model.Section

class CaseReportFormPreview {

    private static final String UNTITLED_FORM_NAME = 'Untitled Case Report Form'
    private final CaseReportForm form

    CaseReportFormPreview(CaseReportForm caseReportForm) {
        this.form = caseReportForm
    }

    void write(OutputStream out) {
        StreamingMarkupBuilder markupBuilder = new StreamingMarkupBuilder()
        out <<  markupBuilder.bind { builder ->
            mkp.yieldUnescaped '<!DOCTYPE html>'
            html(lang:'en') {
                head {
                    meta('http-equiv':'"Content-Type" content="text/html; charset=utf-8"')
                    title(form.name ?: UNTITLED_FORM_NAME)
                    link(rel: 'stylesheet', href: 'https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css','')
                    link(rel: 'stylesheet', href: 'https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css','')
                }
                body {
                    div(class: 'container', style: 'margin-top: 50px;') {
                        div(class: 'jumbotron') {
                            h1(form.name ?: UNTITLED_FORM_NAME)
                            p {
                                strong "Version: "
                                mkp.yield form.version
                            }
                            p {
                                strong "Version Description: "
                                mkp.yield form.versionDescription
                            }
                            p {
                                strong "Revision Notes: "
                                mkp.yield form.revisionNotes
                            }
                        }
                        for (Section section in form.sections.values()) {
                            div(class: "row") {
                                div(class: "col-md-12") {
                                    h1 {
                                        span title: 'Title', section.title
                                        mkp.yield " "
                                        span(class: 'text-muted small pull-right', title: 'Label', section.label)
                                    }
                                    if (section.subtitle) {
                                        h2(title: 'Subtitle') {
                                            em { mkp.yieldUnescaped section.subtitle }
                                        }
                                    }
                                    if (section.instructions) {
                                        div(title: 'Instructions', class: 'alert alert-info') {
                                            mkp.yieldUnescaped section.instructions
                                        }
                                    }
                                    if (section.pageNumber) {
                                        div(title: 'Page Number', class: 'alert alert-info') {
                                            mkp.yieldUnescaped "The section starts at page "
                                            strong section.pageNumber
                                        }
                                    }

                                    for (Item item in section.items.values()) {
                                        if (item.header) {
                                            div(class: 'row') {
                                                div(class: 'col-md-12') {
                                                    h3 item.header, title: "Header"
                                                }
                                            }
                                        }
                                        if (item.subheader) {
                                            div(class: 'row') {
                                                div(class: 'col-md-12') {
                                                    h4 title: "Subheader", {
                                                        strong item.subheader
                                                    }
                                                }
                                            }
                                        }
                                        div(class: 'row') {
                                            div(class: 'col-md-12') {
                                                div(class: 'form-group') {
                                                    h4 {
                                                        mkp.yield item.descriptionLabel
                                                        span(class: 'pull-right small text-muted', item.name)
                                                    }
                                                    CaseReportFormPreview.renderInput(builder, item)
                                                }

                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void renderInput(builder, Item item) {
        switch (item.responseType) {
            case ResponseType.TEXT:
                builder.input type: 'text', class: 'form-control', id: item.name, value: item.defaultValue ?: ''
                break;
            case ResponseType.TEXTAREA:
                builder.textarea class: 'form-control', id: item.name, item.defaultValue ?: ''
                break;
            case ResponseType.SINGLE_SELECT:
                builder.select(class: 'form-control', id: item.name) {
                    renderOptions(builder, item)
                }
                break;
            case ResponseType.MULTI_SELECT:
                builder.select(class: 'form-control', id: item.name, multiple: '') {
                    renderOptions(builder, item)
                }
                break;
            case ResponseType.FILE:
                builder.input type: 'file', class: 'form-control', id: item.name, value: item.defaultValue ?: ''
                break;
            case ResponseType.CHECKBOX:
                builder.input type: 'checkbox', class: 'form-control', id: item.name
                break;
            case ResponseType.RADIO:
                builder.input type: 'radio', class: 'form-control', id: item.name
                break;
            case ResponseType.CALCULATION:
                builder.div class: 'alert alert-info', {
                    mkp.yield "This field is CALCULATION "
                    code item.calculation
                }
                break;
            case ResponseType.GROUP_CALCULATION:
                builder.div class: 'alert alert-info', {
                    mkp.yield "This field is GROUP_CALCULATION "
                    code item.calculation
                }
                break;
            case ResponseType.INSTANT_CALCULATION:
                builder.div class: 'alert alert-info',  {
                    mkp.yield "This field is INSTANT_CALCULATION "
                    code item.calculation
                }
                break;
            default:
                builder.div class: 'alert alert-info', "This field is $item.responseType."
                break;
        }
    }

    private static void renderOptions(builder, Item item) {
        if (item.defaultValue && !(item.defaultValue in item.responseOptions*.value)) {
            builder.option(value: item.defaultValue, item.defaultValue)
        }
        for (ResponseOption responseOption in item.responseOptions) {
            Map<String, Object> args = [value: responseOption.value]
            if (item.defaultValue == responseOption.value) {
                args.selected = 'selected'
            }
            builder.option(args, responseOption.text)
        }
    }

}
