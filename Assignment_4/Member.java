package Assignment_5;

import java.time.LocalDate;

class Member {
    int memberId;
    String name;
    Book issuedBook = null;
    LocalDate issueDate;
    LocalDate dueDate;

    Member(int id, String n) {
        this.memberId = id;
        this.name = n;
    }
}
