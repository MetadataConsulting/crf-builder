package org.modelcatalogue.crf.preview

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.modelcatalogue.crf.model.CaseReportForm
import org.modelcatalogue.crf.serializer.CaseReportFormSerializer
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class CaseReportFormPreviewSpec extends Specification {

    def "test preview rendered for #name"() {
        URL complexFormFileURL = CaseReportFormPreviewSpec.getResource(name + '.crf')

        expect:
        complexFormFileURL

        when:
        CaseReportForm form = CaseReportForm.load(new File(complexFormFileURL.toURI()))
        ByteArrayOutputStream out = new ByteArrayOutputStream()
        new CaseReportFormPreview(form).write(out)
        String html = out.toString()

        File testFolder = new File(System.getProperty('user.dir'), 'crf-builder-preview')
        testFolder.mkdirs()
        File testFile = new File(testFolder, name + '.html')
        testFile.text = html

        println "Test file created at: $testFile.canonicalPath"

        new CaseReportFormSerializer(form).write(new File(testFolder, name + '.xls').newOutputStream())

        then:
        html

        when:
        Document document = Jsoup.parse(html)

        then:
        document

        where:
        name << ['valid', 'invalid']
    }

}
