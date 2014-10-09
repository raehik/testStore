testStore
=========

`testStore` is a set of Java classes designed for storing, accessing and
displaying student test results. Students and tests can be added, edited and
removed at will. Tests can be searched for by name or date set, and you can
display any number of tests of any number of students.


API
---

### TestDatabase

    void setStudentResult(int sId, int tId, int percent)
Set a student's test result to `percent`. If `percent` is not between 0-100, an
`IndexOutOfBoundsException` is thrown.

    Integer[] getAllStudentIds()
Return an iterable Integer[] array of all active student IDs.

    Integer[] getAllTestIds()
Return an iterable Integer[] array of all active test IDs.

    Integer[] getTestIdsInRage(String date1, Stringdate2)
Return an iterable Integer[] array of all the tests between (TODO: inclusive???
two dates in `dd/mm/yy` format.

    String toGrade(int percent)
Return the string representing the grade the percent is mapped to.
