package org.modelcatalogue.crf.builder.util

class ValidationFunctionDelegate {

    static String gt(double d) { "func: gt($d)"}
    static String lt(double d) { "func: lt($d)"}
    static String gte(double d)  { "func: gte($d)"}
    static String lte(double d)  { "func: lte($d)"}
    static String ne(double d) { "func: ne($d)"}
    static String eq(double d)  { "func: eq($d)"}
    static String range(double from, double to) { "func: range($from, $to)"}
    static String gt(long d) { "func: gt($d)"}
    static String lt(long d) { "func: lt($d)"}
    static String gte(long d)  { "func: gte($d)"}
    static String lte(long d)  { "func: lte($d)"}
    static String ne(long d) { "func: ne($d)"}
    static String eq(long d)  { "func: eq($d)"}
    static String range(long from, long to) { "func: range($from, $to)"}

}
