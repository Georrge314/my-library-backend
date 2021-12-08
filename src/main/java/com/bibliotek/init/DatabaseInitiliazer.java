package com.bibliotek.init;

import com.bibliotek.domain.dto.author.EditAuthorRequest;
import com.bibliotek.domain.dto.book.EditBookRequest;
import com.bibliotek.domain.dto.user.CreateUserRequest;
import com.bibliotek.domain.model.Role;
import com.bibliotek.service.AuthorService;
import com.bibliotek.service.BookService;
import com.bibliotek.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.bibliotek.domain.model.Genre.*;

@Component
@Slf4j
public class DatabaseInitiliazer implements CommandLineRunner {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    private final List<EditAuthorRequest> authorRequests = List.of(
            new EditAuthorRequest(
                    "Steven King",
                    "American",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e3/Stephen_King%2C_Comicon.jpg/440px-Stephen_King%2C_Comicon.jpg",
                    "Stephen Edwin King (born September 21, 1947) is an American author of horror," +
                            " supernatural fiction, suspense, crime, science-fiction, and fantasy novels." +
                            " Described as the \"King of Horror\", a play on his surname and a reference to his high" +
                            " standing in pop culture,[2] his books have sold more than 350 million copies,[3]" +
                            " and many have been adapted into films, television series, miniseries, and comic books. " +
                            "King has published 63 novels, including seven under the pen name Richard Bachman, and five non-fiction books.[4]" +
                            " He has also written approximately 200 short stories," +
                            " most of which have been published in book collections.",
                    Set.of(HORROR.toString(), FANTASY.toString(), DRAMA.toString(), CRIME.toString(), THRILLER.toString())
            ),
            new EditAuthorRequest(
                    "William Shakespeare",
                    "Britisher",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a2/Shakespeare.jpg/440px-Shakespeare.jpg",
                    "William Shakespeare (bapt. 26 April 1564 – 23 April 1616)[a] was an " +
                            "English playwright, poet and actor, widely regarded as the greatest writer in the" +
                            " English language and the world's greatest dramatist.[2][3][4] He is often called " +
                            "England's national poet and the \"Bard of Avon\" (or simply \"the Bard\").[5][b] " +
                            "His extant works, including collaborations, consist of some 39 plays,[c] 154 sonnets," +
                            " three long narrative poems, and a few other verses, some of uncertain authorship" +
                            ". His plays have been translated into every major living language and are performed more" +
                            " often than those of any other playwright.[7] They also continue to be studied and reinterpreted. ",
                    Set.of(COMIC_BOOK.toString(), HISTORY.toString())
            ),
            new EditAuthorRequest(
                    "Agatha Christie",
                    "Britisher",
                    "https://upload.wikimedia.org/wikipedia/commons/c/cf/Agatha_Christie.png",
                    "Dame Agatha Mary Clarissa Christie, Lady Mallowan, DBE (née Miller; " +
                            "15 September 1890 – 12 January 1976) was an English writer known for her" +
                            " 66 detective novels and 14 short story collections, particularly those" +
                            " revolving around fictional detectives Hercule Poirot and Miss Marple." +
                            " She also wrote the world's longest-running play, The Mousetrap, which was " +
                            "performed in the West End from 1952 to 2020, as well as six novels under the " +
                            "pseudonym Mary Westmacott. In 1971, she was made a Dame (DBE) for her contributions" +
                            " to literature. Guinness World Records lists Christie as the best-selling fiction writer" +
                            " of all time, her novels having sold more than two billion copies. ",
                    Set.of(MYSTERY.toString(), THRILLER.toString(), CRIME.toString(), FICTION.toString())
            )
    );

    private final List<EditBookRequest> stevenKingBooks = List.of(
            new EditBookRequest(
                    "It",
                    "t is a 1986 horror novel by American author Stephen King." +
                            " It was his 22nd book and his 17th novel written under his own name. " +
                            "The story follows the experiences of seven children as they are terrorized by" +
                            " an evil entity that exploits the fears of its victims to disguise itself while" +
                            " hunting its prey. \"It\" primarily appears in the form of Pennywise the Dancing " +
                            "Clown to attract its preferred prey of young children. ",
                    "English",
                    Set.of(HORROR.toString(), THRILLER.toString(), FANTASY.toString()),
                    LocalDate.of(1986, 9, 15),
                    "https://upload.wikimedia.org/wikipedia/en/7/78/It_%28Stephen_King_novel_-_cover_art%29.jpg",
                    "0-670-81302-8",
                    "0-670-81302-8",
                    "Viking",
                    null
            ),
            new EditBookRequest(
                    "The Shining",
                    "The Shining is a 1977 horror novel by American author Stephen King. " +
                            "It is King's third published novel and first hardback bestseller; its success " +
                            "firmly established King as a preeminent author in the horror genre. " +
                            "The setting and characters are influenced by King's personal experiences, " +
                            "including both his visit to The Stanley Hotel in 1974 and his struggle with alcoholism." +
                            " The novel was adapted into a 1980 film of the same name. The book was followed by a sequel," +
                            " Doctor Sleep, published in 2013, which was adapted into a film of the same name. ",
                    "English",
                    Set.of(HORROR.toString()),
                    LocalDate.of(1977, 1, 28),
                    "https://upload.wikimedia.org/wikipedia/en/4/4c/Shiningnovel.jpg",
                    "978-0-385-12167-5",
                    "978-0-385-12167-5",
                    "Doubleday",
                    null
            )
    );

    private final List<EditBookRequest> williamShakespeareBooks = List.of(
            new EditBookRequest(
                    "Hamlet",
                    "The Tragedy of Hamlet, Prince of Denmark, often shortened to Hamlet (/ˈhæmlɪt/), " +
                            "is a tragedy written by William Shakespeare sometime between 1599 and 1601. It is Shake" +
                            "speare's longest play, with 29,551 words. Set in Denmark, the play depicts Prince Hamlet" +
                            " and his revenge against his uncle, Claudius, who has murdered Hamlet's father in order" +
                            " to seize his throne and marry Hamlet's mother.\n" +
                            "\n" +
                            "Hamlet is considered among the most powerful and influential works of world literature," +
                            " with a story capable of \"seemingly endless retelling and adaptation by others\".[1]" +
                            " It was one of Shakespeare's most popular works during his lifetime[2] " +
                            "and still ranks among his most performed, topping the performance list of the Royal " +
                            "Shakespeare Company and its predecessors in Stratford-upon-Avon since 1879.[3] It has " +
                            "inspired many other writers—from Johann Wolfgang von Goethe and Charles Dickens to James" +
                            " Joyce and Iris Murdoch—and has been described as \"the world's most filmed story after" +
                            " Cinderella\".[4] ",
                    "English",
                    Set.of(TRAGEDY.toString()),
                    null,
                    "https://upload.wikimedia.org/wikipedia/commons/6/6a/Edwin_Booth_Hamlet_1870.jpg",
                    null,
                    null,
                    null,
                    null
            ),
            new EditBookRequest(
                    "Romeo and Juliet",
                    "Romeo and Juliet is a tragedy written by William Shakespeare " +
                            "early in his career about two young Italian star-crossed lovers whose" +
                            " deaths ultimately reconcile their feuding families. It was among Shakespeare's" +
                            " most popular plays during his lifetime and, along with Hamlet, is one of his most " +
                            "frequently performed plays. Today, the title characters are regarded" +
                            " as archetypal young lovers.",
                    "English",
                    Set.of(TRAGEDY.toString()),
                    null,
                    "",
                    "978-1-947808-03-4",
                    "978-1-947808-03-4",
                    "Nicholas Rowe",
                    null
            )
    );

    private final List<EditBookRequest> agathaChristieBooks = List.of(
            new EditBookRequest(
                    "Murder on the Orient Express",
                    "Murder on the Orient Express is a work of detective fiction by English " +
                            "writer Agatha Christie featuring the Belgian detective Hercule Poirot. It" +
                            " was first published in the United Kingdom by the Collins Crime Club on " +
                            "1 January 1934. In the United States, it was published on 28 February " +
                            "1934,[1][2] under the title of Murder in the Calais Coach, by Dodd, Mead" +
                            " and Company.[3][4] The UK edition retailed at seven shillings and sixpence" +
                            " (7/6)[5] and the US edition at $2.[4]\n" +
                            "\n" +
                            "The elegant train of the 1930s, the Orient Express, is stopped by heavy snowfall." +
                            " A murder is discovered, and Poirot's trip home to London from the Middle" +
                            " East is interrupted to solve the case. The opening chapters of the novel take" +
                            " place in Istanbul. The rest of the novel takes place in Yugoslavia, with the" +
                            " train trapped between Vinkovci and Brod. ",
                    "",
                    Set.of(CRIME.toString()),
                    LocalDate.of(1934, 1, 1),
                    "https://upload.wikimedia.org/wikipedia/en/c/c0/Murder_on_the_Orient_Express_First_Edition_Cover_1934.jpg",
                    null,
                    null,
                    "Collins Crime Club",
                    null
            ),
            new EditBookRequest(
                    "The Mysterious Affair at Styles",
                    "The Mysterious Affair at Styles is a detective novel by British writer Agatha Christie." +
                            " It was written in the middle of the First World War, in 1916, and first published by John" +
                            " Lane in the United States in October 1920[1] and in the United Kingdom by The Bodley Head" +
                            " (John Lane's UK company) on 21 January 1921.[2] ",
                    "English",
                    Set.of(CRIME.toString()),
                    LocalDate.of(1920, 10, 1),
                    "https://upload.wikimedia.org/wikipedia/en/8/8c/Mysterious_affair_at_styles.jpg",
                    null,
                    null,
                    "John Lane",
                    null
            )
    );

    private final List<String> usernames = List.of(
            "gesh.petrov@bs.io",
            "mario.hulca@bs.io",
            "ivan.kiriazov@bs.io",
            "viktor.georgiev@bs.io"
    );

    private final List<String> emails = List.of(
            "georgiPetrov314@gmail.com",
            "marioiii@abv.bg",
            "ivan_kiriazov.@hotmail.com",
            "viktor_98@gmail.com"
    );

    private final List<String> fullNames = List.of(
            "Georgi Petrov",
            "Mario Hulca",
            "Ivan Kiriazov",
            "Viktor Antonov"
    );

    private final List<String> roles = List.of(
            Role.USER_ADMIN,
            Role.BOOK_ADMIN,
            Role.AUTHOR_ADMIN,
            Role.COMMENT_ADMIN
    );

    private final String password = "test112233";

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < usernames.size(); i++) {
            CreateUserRequest request = new CreateUserRequest();
            request.setUsername(usernames.get(i));
            request.setEmail(emails.get(i));
            request.setFullName(fullNames.get(i));
            request.setPassword(password);
            request.setRePassword(password);
            if (i == 0) {
                request.setAuthorities(new HashSet<>(roles));
            } else {
                request.setAuthorities(Set.of(roles.get(i)));
            }

            userService.upsert(request);
        }

        for (EditAuthorRequest authorRequest : authorRequests) {
            authorService.createAuthor(authorRequest);
        }

        for (EditBookRequest book : stevenKingBooks) {
            book.setAuthorIds(List.of(authorService.getAuthorId("Steven King")));
            bookService.createBook(book);
        }

        for (EditBookRequest book : williamShakespeareBooks) {
            book.setAuthorIds(List.of(authorService.getAuthorId("William Shakespeare")));
            bookService.createBook(book);
        }

        for (EditBookRequest book : agathaChristieBooks) {
            book.setAuthorIds(List.of(authorService.getAuthorId("Agatha Christie")));
            bookService.createBook(book);
        }
    }
}
