package org.modelcatalogue.crf.preview

import groovy.xml.StreamingMarkupBuilder
import org.modelcatalogue.crf.model.CaseReportForm
import org.modelcatalogue.crf.model.DataType
import org.modelcatalogue.crf.model.DisplayStatus
import org.modelcatalogue.crf.model.GridGroup
import org.modelcatalogue.crf.model.Group
import org.modelcatalogue.crf.model.Item
import org.modelcatalogue.crf.model.ResponseLayout
import org.modelcatalogue.crf.model.ResponseOption
import org.modelcatalogue.crf.model.ResponseType
import org.modelcatalogue.crf.model.Section

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator

class CaseReportFormPreview {

    private static final String UNTITLED_FORM_NAME = 'Untitled Case Report Form'
    private final CaseReportForm caseReportForm

    CaseReportFormPreview(CaseReportForm caseReportForm) {
        this.caseReportForm = caseReportForm
    }

    void write(OutputStream out) {
        StreamingMarkupBuilder markupBuilder = new StreamingMarkupBuilder()

        out <<  markupBuilder.bind { builder ->
            mkp.yieldUnescaped '<!DOCTYPE html>'
            html(lang:'en') {
                head {
                    meta('http-equiv':'"Content-Type" content="text/html; charset=utf-8"')
                    title(caseReportForm.name ?: UNTITLED_FORM_NAME)
                    link(rel: 'stylesheet', href: 'https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css','')
                    link(rel: 'stylesheet', href: 'https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css','')

                    style {
                        //language=CSS
                        mkp.yieldUnescaped '''
                            .popover, .popover-content {
                                width: 400px;
                                min-width: 700px;
                            }
                            .table-fixed {
                                table-layout: fixed;
                            }
                            .container-fluid {
                                margin-top: 15px;
                            }
                            .units {
                                font-size: 12px;
                                padding-top: 6px;
                            }
                            .question-number::after {
                                content: " ";
                            }
                            .control-label {
                                text-align: left!important;
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
                    div(class: 'container-fluid') {
                        mkp.yieldUnescaped '''<a href="https://github.com/MetadataRegistry/crf-builder/"><img style="position: absolute; top: 0; right: 0; border: 0;" src="https://camo.githubusercontent.com/a6677b08c955af8400f44c6298f40e7d19cc5b2d/68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f6769746875622f726962626f6e732f666f726b6d655f72696768745f677261795f3664366436642e706e67" alt="Fork me on GitHub" data-canonical-src="https://s3.amazonaws.com/github/ribbons/forkme_right_gray_6d6d6d.png"></a>'''
                        div(class: 'jumbotron') {
                            h1(caseReportForm.name ?: UNTITLED_FORM_NAME)
                            p {
                                strong "Version: "
                                mkp.yield caseReportForm.version
                            }
                            p {
                                strong "Version Description: "
                                mkp.yield caseReportForm.versionDescription
                            }
                            p {
                                strong "Revision Notes: "
                                mkp.yield caseReportForm.revisionNotes
                            }
                        }

                        renderViolations(builder, validate(caseReportForm))

                        ul(class: 'nav nav-tabs', role: 'tablist') {
                            for (Section section in caseReportForm.sections.values()) {
                                Map<String, Object> args = [role: 'presentation']
                                if (section == caseReportForm.sections.values().first()) {
                                    args.class = 'active'
                                }
                                li(args) {
                                    a(section.label, 'data-toggle': 'tab', role: 'tab', href: "#${getSectionIdFromLabel(section)}", 'aria-controlls': getSectionIdFromLabel(section))
                                }
                            }
                        }
                        div(class: 'tab-content') {
                            for (Section section in caseReportForm.sections.values()) {
                                Map<String, Integer> counts = getCountOfItemsPerRow(section)
                                Map<String, Object> args = [class: "row tab-pane", role: "tabpanel", id: getSectionIdFromLabel(section)]
                                if (section == caseReportForm.sections.values().first()) {
                                    args['class'] = "${args['class']} active"
                                }
                                div(args) {
                                    div(class: "col-md-12") {
                                        renderViolations(builder, validate(section))

                                        for (Group group in section.groups.values()) {
                                            if (!(group instanceof GridGroup)) {
                                                Set<ConstraintViolation> errors = validate(group)
                                                if (errors) {
                                                    div(class: 'alert alert-danger', "There are errors in $group.label group")
                                                    renderViolations(builder, errors)
                                                }
                                            }
                                        }

                                        printHeadersAndInstructions(builder, section)

                                        GridGroup grid = null

                                        for (Item item in section.items.values()) {
                                            if (item.group && item.group == grid) {
                                                continue
                                            }

                                            if (item.group instanceof GridGroup) {
                                                grid = item.group as GridGroup
                                                if (grid.header) {
                                                    div(class: 'row') {
                                                        div(class: 'col-md-12') {
                                                            h3(title: "Grid Header") {
                                                                mkp.yield grid.header
                                                                mkp.yield " "
                                                                if (grid.displayStatus == DisplayStatus.HIDE) {
                                                                    span(class: 'fa fa-fw fa-eye-slash text-muted small', title: 'Hidden', '')
                                                                    mkp.yield ' '
                                                                }
                                                                span(class: 'text-muted small pull-right', title: 'Label', grid.label)
                                                            }
                                                        }
                                                    }
                                                }
                                                renderViolations(builder, validate(grid))
                                            }

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

                                            if (grid) {
                                                table (class: 'table table-fixed') {
                                                    thead {
                                                        tr {
                                                            for (Item gridItem in grid.items.values()) {
                                                                th(addDataItemPopover(gridItem, class: getCellClassForCount(grid.items.size()))) {
                                                                    renderLabel(builder, gridItem, 'col-md-12')
                                                                }
                                                            }
                                                        }
                                                    }
                                                    tbody {
                                                        (grid.repeatNum ?: 1).times {
                                                            builder.tr {
                                                                for (Item gridItem in grid.items.values()) {
                                                                    td class: getCellClassForCount(grid.items.size()), {
                                                                        renderInput(builder, gridItem, 'col-md-9')
                                                                        renderUnitsAndRightItemText(builder, gridItem, false)
                                                                    }
                                                                }
                                                            }

                                                        }
                                                    }
                                                }

                                                continue
                                            }

                                            Integer count = counts[item.name]

                                            if (!item.columnNumber || item.columnNumber == 1) {
                                                mkp.yieldUnescaped '<div class="row">'
                                            }
                                            div(class: getCellClassForCount(count)) {
                                                form(class: 'form-horizontal') {
                                                    div(addDataItemPopover(class: 'form-group form-group-sm', item)) {
                                                        renderLabel(builder, item, 'col-md-3')
                                                        renderInput(builder, item, 'col-md-6')
                                                        renderUnitsAndRightItemText(builder, item, true)
                                                    }
                                                }
                                            }
                                            if (!item.columnNumber || item.columnNumber == count) {
                                                mkp.yieldUnescaped '</div>'
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

    private static void renderViolations(builder, Set<ConstraintViolation> constraintViolations) {
        if (constraintViolations) {
            builder.div(class: 'row') {
                for (ConstraintViolation violation in constraintViolations) {
                    div(class: 'col-md-12') {
                        div(class: 'alert alert-danger') {
                            mkp.yield "'$violation.propertyPath' "
                            mkp.yield violation.message
                        }
                    }
                }
            }
        }
    }

    private static Set<ConstraintViolation> validate(Object what) {
        try {
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            return validator.validate(what)
        } catch (e) {
            System.err.println """
                Cannot create validator: $e
                Is there any Java Validation API implementation present in your application?

                You can add for example following libaries to you project to enable validaton:
                    compile 'javax.el:javax.el-api:2.2.5'
                    compile 'org.glassfish.web:javax.el:2.2.4'
                    compile 'org.hibernate:hibernate-validator:5.1.3.Final'
            """.stripIndent().trim()
            return Collections.emptySet()
        }
    }

    private static void printHeadersAndInstructions(builder, Section section) {
        builder.with {
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
        }
    }

    private static String getSectionIdFromLabel(Section section) {
        section.label.replaceAll('\\W', '_')
    }

    private static void renderInput(builder, Item item, String clazz) {
        builder.div(class: clazz) {
            switch (item.responseType) {
                case ResponseType.TEXT:
                    switch (item.dataType) {
                        case DataType.INT:
                        case DataType.REAL:
                            builder.input type: 'number', class: 'form-control input-sm', id: item.name, name: item.name, value: item.defaultValue ?: ''
                            break
                        case DataType.DATE:
                        case DataType.PDATE:
                            builder.input type: 'date', class: 'form-control input-sm', id: item.name, name: item.name, value: item.defaultValue ?: ''
                            break
                        case DataType.ST:
                        default:
                            builder.input type: 'text', class: 'form-control input-sm', id: item.name, name: item.name, value: item.defaultValue ?: ''
                            break
                    }
                    break;
                case ResponseType.TEXTAREA:
                    builder.textarea class: 'form-control input-sm', id: item.name, name: item.name, item.defaultValue ?: '', rows: 5
                    break;
                case ResponseType.SINGLE_SELECT:
                    builder.select(class: 'form-control input-sm', id: item.name, name: item.name) {
                        renderOptions(builder, item)
                    }
                    break;
                case ResponseType.MULTI_SELECT:
                    builder.select(class: 'form-control input-sm', id: item.name, name: item.name, multiple: '') {
                        renderOptions(builder, item)
                    }
                    break;
                case ResponseType.FILE:
                    builder.input type: 'file', class: 'form-control input-sm', id: item.name, name: item.name, value: item.defaultValue ?: ''
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
                        mkp.yieldUnescaped "This field is CALCULATION<br/>"
                        code item.calculation
                    }
                    break;
                case ResponseType.GROUP_CALCULATION:
                    builder.div class: 'alert alert-info', {
                        mkp.yieldUnescaped "This field is GROUP_CALCULATION<br/>"
                        code item.calculation
                    }
                    break;
                case ResponseType.INSTANT_CALCULATION:
                    builder.div class: 'alert alert-info',  {
                        mkp.yieldUnescaped "This field is INSTANT_CALCULATION<br/>"
                        code item.calculation
                    }
                    break;
                default:
                    builder.div class: 'alert alert-info', "This field is $item.responseType."
                    break;
            }
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

        if (item.descriptionLabel) {
            builder << '<span class="fa fa-fw fa-info-circle"></span> ' << item.descriptionLabel << '\n'
        }

        if (item.pageNumber) {
            builder << '<span class="fa fa-fw fa-file-text-o"></span> Page number <code>' << item.pageNumber << '</code>\n'
        }

        if (item.widthDecimal) {
            if (item.dataType == DataType.ST) {
                builder << '<span class="fa fa-fw fa-hashtag"></span> The string can contain up to <code>' << item.widthDecimal[0..-4] << '</code> characters\n'
            } else if (item.dataType == DataType.INT) {
                builder << '<span class="fa fa-fw fa-hashtag"></span> The number can contain <code>' << item.widthDecimal[0..-4] << '</code> digits\n'
            } else if (item.dataType == DataType.REAL) {
                def match = item.widthDecimal =~ /(\d+)\((\d+)\)/
                if (match) {
                    builder << '<span class="fa fa-fw fa-hashtag"></span> The number can contain <code>' << match[0][1] << '</code> digits (with <code>' << match[0][2] << ' digit(s) after decimal point.</code> \n'
                }
            }
        }

        if (item.group && !(item.group instanceof GridGroup)) {
            builder << '<span class="fa fa-fw fa-object-group text-muted small" title="Group"></span> This item belongs to <code>' << item.group.label << '</code> group\n'
        }

        if (item.displayStatus == DisplayStatus.HIDE) {
            builder << '<span class="fa fa-fw fa-eye-slash"></span> Hidden '
            if (item.simpleConditionalDisplay) {
                builder << '(this item is shown when <code>' << (item.conditionalDisplay.response.item.leftItemText ?: item.conditionalDisplay.response.item.name) << '</code> is '
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

    private static String getCellClassForCount(int count) {
        switch (count) {
            case [0,1]: return "col-md-12"
            case 2: return "col-md-6"
            case 3: return "col-md-4"
            case [4,5]: return "col-md-3"
            case 6..11: return "col-md-2"
            default: return "col-md-1"
        }
    }

    private static void renderLabel(builder, Item item, String clazz) {
        builder.label(for: item.name, class: "control-label $clazz") {
            if (item.questionNumber) {
                mkp.yield(item.questionNumber)
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

            if (item.pageNumber) {
                span(class: 'fa fa-fw fa-file-text-o text-muted small', title: 'Page Number', '')
                mkp.yield ' '
            }

            if (item.widthDecimal) {
                span(class: 'fa fa-fw fa-hashtag text-muted small', title: 'Format', '')
                mkp.yield ' '
            }

            if (item.group && !(item.group instanceof GridGroup)) {
                span(class: 'fa fa-fw fa-object-group text-muted small', title: 'Group', '')
                mkp.yield ' '
            }
        }
    }

    private static void renderUnitsAndRightItemText(builder, Item item, boolean renderRightItemText) {
        StringBuilder sb = new StringBuilder()
        if (item.units) {
            sb << "$item.units "
        }

        if (item.rightItemText && renderRightItemText) {
            sb << item.rightItemText
        }

        builder.span(class: 'col-md-3 units', sb.toString())
    }

    private static Map<String, Object> addDataItemPopover(Map<String, Object> args = [:], Item item) {
        args.putAll 'data-content': buildPopover(item),
                title: item.name,
                'data-toggle': 'popover',
                'data-trigger': 'hover',
                'data-placement': 'top',
                'data-html': 'true',
                'data-container': 'body',
                'data-viewport': "#${getSectionIdFromLabel(item.section)}"
        return args
    }


    private static Map<String, Integer> getCountOfItemsPerRow(Section section) {
        Map<String, Integer> counts = [:].withDefault { 1 }

        List<Item> currentRow = []
        for (Item item in section.items.values()) {
            if ((item.columnNumber ?: 1) == 1) {
                for (Item i in currentRow) {
                    counts[i.name] = currentRow.size()
                }
                currentRow.clear()
            }
            currentRow << item
        }

        return counts
    }
}
