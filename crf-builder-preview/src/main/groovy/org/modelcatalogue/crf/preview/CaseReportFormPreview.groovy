package org.modelcatalogue.crf.preview

import groovy.xml.StreamingMarkupBuilder
import org.modelcatalogue.crf.model.CaseReportForm
import org.modelcatalogue.crf.model.DisplayStatus
import org.modelcatalogue.crf.model.Item
import org.modelcatalogue.crf.model.ResponseLayout
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

                    style {
                        //language=CSS
                        mkp.yieldUnescaped '''
                            .popover, .popover-content {
                                width: 400px;
                                min-width: 700px;
                            }
                        '''.stripIndent().trim()
                    }

                    script(src: "https://code.jquery.com/jquery-1.12.3.min.js", integrity: "sha256-aaODHAgvwQW1bFOGXMeX+pC4PZIPsvn2h1sArYOhgXQ=", crossorigin: "anonymous", '')
                    script(src: "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js", '')
                    script(type: 'text/javascript') {
                        //language=JavaScript
                        mkp.yieldUnescaped '''
                            $(function () {
                              $('[data-toggle="popover"]').popover()
                            })
                        '''.stripIndent().trim()
                    }
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
                        ul(class: 'nav nav-tabs', role: 'tablist') {
                            for (Section section in form.sections.values()) {
                                Map<String, Object> args = [role: 'presentation']
                                if (section == form.sections.values().first()) {
                                    args.class = 'active'
                                }
                                li(args) {
                                    a(section.label, 'data-toggle': 'tab', role: 'tab', href: "#${section.label.replaceAll('\\W','_')}", 'aria-controlls': section.label.replaceAll('\\W','_'))
                                }
                            }
                        }
                        div(class: 'tab-content') {
                            for (Section section in form.sections.values()) {
                                Map<String, Object> args = [class: "row tab-pane", role: "tabpanel", id: section.label.replaceAll('\\W','_') ]
                                if (section == form.sections.values().first()) {
                                    args['class'] = "${args['class']} active"
                                }
                                div(args) {
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
                                                    div(class: 'form-group', 'data-content': buildPopover(item), title: item.name, 'data-toggle': 'popover', 'data-trigger': 'hover', 'data-placement': 'top', 'data-html': 'true') {
                                                        label(for: item.name) {
                                                            if (item.questionNumber) {
                                                                mkp.yield item.questionNumber
                                                                mkp.yield ' '
                                                            }
                                                            if (item.leftItemText) {
                                                                mkp.yield(item.leftItemText)
                                                                mkp.yield ' '
                                                            }
                                                            if (item.required) {
                                                                span(class: 'fa fa-fw fa-asterisk text-muted small', title: 'Required', '')
                                                                mkp.yield ' '
                                                            }
                                                            if (item.phi) {
                                                                span(class: 'fa fa-fw fa-lock text-muted small', title: 'Protected Health Information', '')
                                                                mkp.yield ' '
                                                            }
                                                            if (item.displayStatus == DisplayStatus.HIDE) {
                                                                span(class: 'fa fa-fw fa-eye-slash text-muted small', title: 'Hidden', '')
                                                                mkp.yield ' '
                                                            }
                                                            if (item.validation) {
                                                                span(class: 'fa fa-fw fa-check-circle text-muted small', title: 'Validation', '')
                                                                mkp.yield ' '
                                                            }
                                                        }
                                                        CaseReportFormPreview.renderInput(builder, item)
                                                        if (item.units) {
                                                            span("$item.units")
                                                        }
                                                        if (item.rightItemText) {
                                                            span(item.rightItemText)
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
        }
    }

    private static void renderInput(builder, Item item) {
        switch (item.responseType) {
            case ResponseType.TEXT:
                builder.input type: 'text', class: 'form-control', id: item.name, name: item.name, value: item.defaultValue ?: ''
                break;
            case ResponseType.TEXTAREA:
                builder.textarea class: 'form-control', id: item.name, name: item.name, item.defaultValue ?: ''
                break;
            case ResponseType.SINGLE_SELECT:
                builder.select(class: 'form-control', id: item.name, name: item.name) {
                    renderOptions(builder, item)
                }
                break;
            case ResponseType.MULTI_SELECT:
                builder.select(class: 'form-control', id: item.name, name: item.name, multiple: '') {
                    renderOptions(builder, item)
                }
                break;
            case ResponseType.FILE:
                builder.input type: 'file', class: 'form-control', id: item.name, name: item.name, value: item.defaultValue ?: ''
                break;
            case ResponseType.CHECKBOX:
                for (ResponseOption option in item.responseOptions) {
                    builder.div(class: item.responseLayout == ResponseLayout.HORIZONTAL ? 'checkbox-inline' : 'checkbox') {
                        label {
                            Map<String, Object> args = [type: 'checkbox', name: item.name, value: option.value]
                            if (item.defaultValue == option.value) {
                                args.checked = 'checked'
                            }
                            input args
                            mkp.yieldUnescaped "$option.text <span class='text-muted'>[$option.value]</span>"
                        }
                    }
                }
                break;
            case ResponseType.RADIO:
                for (ResponseOption option in item.responseOptions) {
                    builder.div(class: item.responseLayout == ResponseLayout.HORIZONTAL ? 'radio-inline' : 'radio') {
                        label {
                            Map<String, Object> args = [type: 'radio', name: item.name, value: option.value]
                            if (item.defaultValue == option.value) {
                                args.checked = 'checked'
                            }
                            input args
                            mkp.yieldUnescaped "$option.text <span class='text-muted'>[$option.value]</span>"
                        }
                    }
                }
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
            builder.option(args, "$responseOption.text [$responseOption.value]")
        }
    }

    private static String buildPopover(Item item) {
        StringBuilder builder =  new StringBuilder()
        builder << '<div style="white-space: pre-line;">'

        if (item.required) {
            builder << '<span class="fa fa-fw fa-asterisk"></span> Required' << '\n'
        }

        if (item.dataType) {
            builder << '<span class="fa fa-fw fa-table"></span> '<< item.dataType.humanReadable << '\n'
        }

        if (item.phi) {
            builder << '<span class="fa fa-fw fa-lock"></span> Protected Health Information' << '\n'
        }

        builder << '<span class="fa fa-fw fa-info-circle"></span> ' << item.descriptionLabel << '\n'

        if (item.displayStatus == DisplayStatus.HIDE) {
            builder << '<span class="fa fa-fw fa-eye-slash"></span> Hidden '
            if (item.simpleConditionalDisplay) {
                builder << '(this item is shown when <code>' << item.conditionalDisplay.response.item.name << '</code> is '
                builder << '<code>' << item.conditionalDisplay.response.text << '<span class="text-muted">['
                builder << item.conditionalDisplay.response.value << ']</span>' << '</code> with instructions '
                builder << '<span class="text-warning">"' << item.conditionalDisplay.message << '"<span>'
                builder << ')'
            }

            builder << '\n'

        }

        if (item.validation) {
            builder << '<span class="fa fa-fw fa-check-circle"></span> Validation: <code>' << item.validation << '</code>'
            if (item.validationErrorMessage) {
                builder << ' (<span class="text-warning">' << item.validationErrorMessage << '</span>)'
            }
            builder << '\n'

        }

        builder << '</div>'
        return builder.toString()
    }
}
