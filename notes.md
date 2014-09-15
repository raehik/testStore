Students stored as numbers.
Tests stored as numbers.
Results stored as integer percentage i.e. 0-100


USER INPUT
x20:
- name
- % test result
- test label

addTest(String name, int percent)

OUTPUT
- grades
    - later:
        - tables
        - colour



Hash<Key,Value> = new Hash<int, List<int>>
123456789 -> [54, 23, -1, 0]
    -1 : did not do test


Simple:
- enter test name
- enter 



Options:
    1. add student
    2. add test
  ( - delete test ) -- nullify?
  ( - delete student ) -- remove key-value
  ( - modify test )

Data:
    1. student (identifier) results-list **hashmap** -- Key = int, Value 
{{ 12345 -> {{ test-identifier-1 -> their-result, 2 -> result-2 }} }}
    2. student-identifier -> student name **list**
{{ 12345 -> "John Doe" }}
    3. test-identifier -> test-name
{{ 123456 -> "Maths"


Public methods:
    - student name, test names, test percentages
    - toGrade using fixed grade conversion table

