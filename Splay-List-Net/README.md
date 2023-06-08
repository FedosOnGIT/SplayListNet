# Дипломная работа

Код проекта делится на 2 части:

1. [Реализации алгоритмов и их комплектующие](src/main/kotlin).
2. [Тесты алгоритмов и результаты тестирования](src/test)

Опишем модули подробнее.

## Реализации и комплектующие

1. [model](src/main/kotlin/model) - модели вершин, используемых в различных реализациях сетей.
2. [structures](src/main/kotlin/structures) - различные вариации коммуникационных сетей. Содержит
   - [SkipListNet](src/main/kotlin/structures/head_dependent/skipList/SkipListNet.kt) - статический вариант
коммуникационной сети, основан на `SkipList`
   - [SimpleSplayListNet](src/main/kotlin/structures/head_dependent/splayNet/SimpleSplayListNet.kt) - базовый вариант
динамической сети, основан на `SplayList`. Каждый запрос посылается через `head`.
   - [TreeSplayListNet](src/main/kotlin/structures/head_dependent/splayNet/TreeSplayListNet.kt) - вариант динамической
сети, посылающий запросы через наименьшего общего предка.
   - [LeftRightSplayNet](src/main/kotlin/structures/head_independent/model/LeftRightSplayNet.kt) - динамическая сеть,
способная посылать запросы из одной своей половины в другую.
   - [ParentChildNet](src/main/kotlin/structures/head_independent/ParentChildNet.kt) - дерево из `LeftRightSplayNet`,
ищущее первую вершину, которая может удовлетворить запрос.
   - [probability](src/main/kotlin/structures/probability) - модуль содержащие вероятностные адаптивные сети, проводящие
обновления с определённой вероятностью `p`. Наиболее значимым здесь является
[ProbabilityFrontTreeSplayListNet](src/main/kotlin/structures/probability/ProbabilityFrontTreeSplayListNet.kt), который
в случае, когда обновление не происходит - посылает запрос напрямую старта до финиша.
3. [utils](src/main/kotlin/utils) - различные инструменты, реализующие общий код алгоритмов,
как например функции `update` или `find`.

## Тесты алгоритмов и результаты тестирования

1. [Сами тесты](src/test/kotlin). Среди них основные:
    - [RepeatedSending](src/test/kotlin/RepeatedSending.kt) - основные тесты,
сравнивают скорость работы невероятностных алгоритмов в зависимости от частоты повторения запроса.
    - [ProbabilitySending](src/test/kotlin/ProbabilitySending.kt) - сравнивает скорость работы алгоритма в зависимости
от частоты повторения запроса и вероятности обновления.
    - [RealTraceSending](src/test/kotlin/RealTraceTest.kt) - тестирование на реальных запросах, представленных в разделе
`traces`, пишутся в `traces_results`.
2. [traces](src/test/resources/traces) - примеры реальных запросов. В начале каждого документа указано количество вершин 
и количество запросов. Каждый запрос характеризуется началом и концом запроса.
3. [traces_results](src/test/resources/traces_results) - результат отработки `RealTraceTest` на `traces`.