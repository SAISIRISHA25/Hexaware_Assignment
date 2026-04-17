package com.example.mba;

import java.util.List;
import java.util.Scanner;

import com.example.mba.entity.Member;
import com.example.mba.entity.Movie;
import com.example.mba.service.BookingService;
import com.example.mba.service.MemberService;
import com.example.mba.service.MovieService;
import com.example.mba.util.HibernateUtil;

public class MovieBookingApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        MovieService movieService = new MovieService();
        MemberService memberService = new MemberService();
        BookingService bookingService = new BookingService();

        int choice;

        do {
            System.out.println("\n===== MOVIE BOOKING APP =====");
            System.out.println("1. Add Movie");
            System.out.println("2. Show All Movies");
            System.out.println("3. Search Movie by Id");
            System.out.println("4. Update Price");
            System.out.println("5. Delete Movie");
            System.out.println("6. Add Member");
            System.out.println("7. Show All Members");
            System.out.println("8. Search Member by Id");
            System.out.println("9. Delete Member");
            System.out.println("10. Book Ticket");
            System.out.println("11. Cancel Ticket");
            System.out.println("12. Show Booking Details");
            System.out.println("13. Total Amount");
            System.out.println("14. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Movie Id: ");
                    int movieId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Movie Name: ");
                    String movieName = sc.nextLine();
                    System.out.print("Enter Price: ");
                    double price = sc.nextDouble();
                    System.out.print("Enter Seats: ");
                    int seats = sc.nextInt();

                    movieService.addMovie(new Movie(movieId, movieName, price, seats));
                    System.out.println("Movie added successfully");
                    break;

                    case 2:
                    List<Movie> movies = movieService.showAllMovies();
                    for (Movie m : movies) {
                        System.out.println(m);
                    }
                    break;

                    case 3:
                    System.out.print("Enter Movie Id: ");
                    int searchMovieId = sc.nextInt();
                    Movie movie = movieService.searchMovieById(searchMovieId);
                    System.out.println(movie != null ? movie : "Movie not found");
                    break;

                    case 4:
                    System.out.print("Enter Movie Id: ");
                    int upId = sc.nextInt();
                    System.out.print("Enter New Price: ");
                    double newPrice = sc.nextDouble();
                    System.out.println(movieService.updatePrice(upId, newPrice) + " movie updated");
                    break;

                    case 5:
                    System.out.print("Enter Movie Id: ");
                    int delMovieId = sc.nextInt();
                    System.out.println(movieService.deleteMovie(delMovieId) + " movie deleted");
                    break;

                case 6:
                    System.out.print("Enter Member Id: ");
                    int memberId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Member Name: ");
                    String memberName = sc.nextLine();

                    memberService.addMember(new Member(memberId, memberName, 0, 0));
                    System.out.println("Member added successfully");
                    break;

                case 7:
                    List<Member> members = memberService.showAllMembers();
                    for (Member m : members) {
                        System.out.println(m);
                    }
                    break;

                case 8:
                    System.out.print("Enter Member Id: ");
                    int searchMemberId = sc.nextInt();
                    Member member = memberService.searchMemberById(searchMemberId);
                    System.out.println(member != null ? member : "Member not found");
                    break;

                case 9:
                    System.out.print("Enter Member Id: ");
                    int delMemberId = sc.nextInt();
                    System.out.println(memberService.deleteMember(delMemberId) + " member deleted");
                    break;

                case 10:
                    System.out.print("Enter Member Id: ");
                    int bMemberId = sc.nextInt();
                    System.out.print("Enter Movie Id: ");
                    int bMovieId = sc.nextInt();
                    System.out.print("Enter Number of Tickets: ");
                    int tickets = sc.nextInt();
                    System.out.println(bookingService.bookTicket(bMemberId, bMovieId, tickets));
                    break;

                case 11:
                    System.out.print("Enter Member Id: ");
                    int cMemberId = sc.nextInt();
                    System.out.println(bookingService.cancelTicket(cMemberId));
                    break;

                case 12:
                    List<Object[]> bookings = bookingService.showBookingDetails();
                    for (Object[] row : bookings) {
                        System.out.println("Member Id: " + row[0] +
                                ", Member Name: " + row[1] +
                                ", Movie Name: " + row[2] +
                                ", Tickets: " + row[3] +
                                ", Price: " + row[4]);
                    }
                    break;

                case 13:
                    List<Object[]> totals = bookingService.totalAmount();
                    for (Object[] row : totals) {
                        System.out.println("Member Id: " + row[0] +
                                ", Member Name: " + row[1] +
                                ", Movie Name: " + row[2] +
                                ", Tickets: " + row[3] +
                                ", Total Amount: " + row[4]);
                    }
                    break;

                case 14:
                    System.out.println("Exiting...");
                    HibernateUtil.getSessionFactory().close();
                    break;

                    default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 14);

        sc.close();
    }
}

        