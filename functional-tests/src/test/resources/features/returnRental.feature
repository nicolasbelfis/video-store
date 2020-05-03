Feature: rental

  Scenario: return film rental
    Given available film
      | filmId | name  | type | stock |
      | f1     | RAMBO | OLD  | 1     |
    Given customer with data
      | customerID | subscription | bonus |
      | 1          | PREMIUM      | 0     |
    When create a rental with input
      | customerID | filmId | subscription | type | numberOfDays | startDate  |
      | 1          | f1     | PREMIUM      | OLD  | 10           | 2021-05-05 |
    Then return same rental with input
      | customerID | filmId | returnDate |
      | 1          | f1     | 2021-05-16 |
    Then available film view is eventually updated
      | filmId | name  | type | stock |
      | f1     | RAMBO | OLD  | 1     |
    Then customer view is eventually updated with overcharge
      | filmId | customerID | price | numberOfDays | returnDate | overcharge |
      | f1     | 1          | 6     | 11           | 2021-05-16 | 1          |

