package org.modelcatalogue.crf.preview

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.modelcatalogue.crf.model.CaseReportForm
import org.modelcatalogue.crf.serializer.CaseReportFormSerializer
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

        File testFolder = new File(System.getProperty('user.dir'), 'crf-builder-preview')
        testFolder.mkdirs()
        File testFile = new File(testFolder, 'crf-builder-preview.html')
        testFile.text = html

        println "Test file created at: $testFile.canonicalPath"

        new CaseReportFormSerializer(form).write(new File(testFolder, 'crf-builder-preview.xls').newOutputStream())

        then:
        html

        when:
        Document document = Jsoup.parse(html)

        then:
        document

    }

}
