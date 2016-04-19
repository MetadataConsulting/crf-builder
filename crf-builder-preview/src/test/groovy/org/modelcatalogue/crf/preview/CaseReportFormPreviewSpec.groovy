package org.modelcatalogue.crf.preview

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.modelcatalogue.crf.model.CaseReportForm
import spock.lang.Specification

class CaseReportFormPreviewSpec extends Specification {

    def "test preview rendered"() {
        URL complexFormFileURL = CaseReportFormPreviewSpec.getResource('samplePhysicalExamEnglish.crf')

        expect:
        complexFormFileURL

        when:
        CaseReportForm form = CaseReportForm.load(new File(complexFormFileURL.toURI()))
        ByteArrayOutputStream out = new ByteArrayOutputStream()
        new CaseReportFormPreview(form).write(out)
        String html = out.toString()

        new File(System.getProperty('java.io.tmpdir'), 'crf-builder-preview.html').text = html

        then:
        html

        when:
        Document document = Jsoup.parse(html)

        then:
        document

    }

}
