package hr.carpazar.controllerTests;

import hr.carpazar.controllers.UserController;
import hr.carpazar.models.Listing;
import hr.carpazar.models.User;
import hr.carpazar.services.ListingService;
import hr.carpazar.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserTest {
    @Mock
    private UserService userService;
    @Mock
    private ListingService listingService;
    @Mock
    private Model model;

    @InjectMocks
    private UserController userController;

    @Test
    void testHome(){
        HttpSession session = mock(HttpSession.class);
        List<Listing> allListings = createSampleListings();

        when(listingService.getAll()).thenReturn(allListings);
        String result = userController.home(session,model);
        verify(model,times(1)).addAttribute(eq("listings"),anyList());

        assertEquals("home",result);
    }

    @Test
    void testOpenUserPage(){
        HttpSession session = mock(HttpSession.class);
        String loggedInId = "loggedInId";
        String loggedInUsername = "loggedInUsername";
        Optional<String> usernameOptional = Optional.of("otherUser");


        when(session.getAttribute("user_id")).thenReturn(loggedInId);
        when(session.getAttribute("user_username")).thenReturn(loggedInUsername);

        User loggedInUser = new User();
        loggedInUser.setId(loggedInId);
        loggedInUser.setUserName(loggedInUsername);
        loggedInUser.setIsPremium(Boolean.TRUE);

        User otherUser = new User();
        otherUser.setId("otherUserId");
        otherUser.setUserName("otherUser");
        otherUser.setIsPremium(Boolean.TRUE);
        List<Listing> listings = createSampleListings();
        when(userService.findByUserName(otherUser.getUserName())).thenReturn(otherUser);
        when(listingService.findByUserId(otherUser.getId())).thenReturn(listings);

        String result = userController.openUserPage(usernameOptional, model, session);

        verify(model).addAttribute("username","otherUser");
        verify(model).addAttribute("premium",true);
        verify(model).addAttribute("listings",listings);


        assertEquals("otherUser",result);
    }

    private List<Listing> createSampleListings(){

        User user = new User();
        user.setId("no1");
        user.setUserName("firstUser");
        user.setIsPremium(Boolean.TRUE);

        List<Listing> sampleListings = new ArrayList<>();
        Listing listing1 = new Listing();
        Listing listing2 = new Listing();

        listing1.setId("firstListing");
        listing1.setUserId(user);
        listing1.setIsSponsored(Boolean.TRUE);
        listing1.setTitle("The No1 Test Listing!");
        listing2.setId("secondListing");
        listing2.setUserId(user);
        listing2.setIsSponsored(Boolean.FALSE);
        listing2.setTitle("The No2 Test Listing!");

        sampleListings.add(listing1);
        sampleListings.add(listing2);

        return sampleListings;
    }
}