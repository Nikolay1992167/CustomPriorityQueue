<h1 align="center">CustomPriorityQueue</h1>

<details>
 <summary><strong>
  Техническое задание
</strong></summary>

#### ЗАДАНИЕ:

* Написать собственную реализацию PriorityQueue
* Интерфейс должен иметь следующие методы:
* add
* peek
* poll
* Очередь должна поддерживать хранение любых элементов, поэтому используем Java Generics
* Для сравнения элементов использовать Comparable. Все элементы, добавляемые в очередь, должны реализовывать этот интерфейс
* Реализация должна использовать по умолчанию структуру данных "Минимальная двоичная куча", которую разбирали на занятии.
* То есть в вершине лежит минимальный элемент.
* Элементы требуется хранить в обычном Java массиве
* Массив должен динамически расширяться при добавлении новых элементов
* Начальный размер массива - 8
* Должны быть реализованы процедуры siftUp и siftDown.
* Рекурсию использовать нельзя - итерацию осуществляем по индексам
* Добавить возможность указать в конструкторе Comparator и использовать его вместо Comparable:
* - если был предоставлен Comparator, то использовать его
* - если не был предоставлен Comparator, сравнивать по Comparable
* - если нет ни Comparator, ни реализован Comparable, то должно быть выброшено исключение
* В таком случае, следующий код должен вывести 20:
* PriorityQueue<Integer> q = new PriorityQueue<>(Comparator.reverseOrder());
* q.add(10);
* q.add(20);
* System.out.println(q.peek());
Крайне желательно написать хотя бы базовые тесты
</details>

<details>
 <summary><strong>
  Запуск проекта
</strong></summary>

* Пример использования CustomPriorityQueue представлен в классе Main.
* Тесты можно запустить с помощью команды `./gradlew test`.

</details>