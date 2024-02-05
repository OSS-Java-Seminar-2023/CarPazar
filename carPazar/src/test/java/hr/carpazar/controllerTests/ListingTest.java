package hr.carpazar.controllerTests;

import hr.carpazar.controllers.ListingController;
import hr.carpazar.dtos.ListingDto;
import hr.carpazar.models.Chat;
import hr.carpazar.models.Listing;
import hr.carpazar.models.User;
import hr.carpazar.services.ChatService;
import hr.carpazar.services.ListingService;
import hr.carpazar.services.MessageService;
import hr.carpazar.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ListingTest {

    @Mock
    private UserService userService;

    @Mock
    private ListingService listingService;

    @Mock
    private ChatService chatService;

    @Mock
    private MessageService messageService;

    @Mock
    private HttpSession httpSession;
    @Mock
    private Model model;

    @InjectMocks
    private ListingController listingController;



    @Test
    void testDeleteListing(){

        String listingId = "testListingId";
        String userId = "testUserId";
        String username = "testUser";
        HttpSession httpSession = mock(HttpSession.class);
        httpSession.setAttribute("user_id", userId);
        httpSession.setAttribute("user_username", username);

        User loggedInUser = new User();
        loggedInUser.setIsAdmin(false);
        loggedInUser.setId(userId);
        loggedInUser.setUserName(username);
        lenient().when(userService.findByUserName(username)).thenReturn(loggedInUser);

        Listing listing = new Listing();
        listing.setUserId(loggedInUser);
        listing.setId(listingId);
        Optional<Listing> listingOptional = Optional.of(listing);
        lenient().when(listingService.findById(listingId)).thenReturn(listingOptional.get());

        Chat chat1 = new Chat();
        Chat chat2 = new Chat();
        List<Chat> chats = new ArrayList<>();
        chat1.setListingId(listing);
        chat2.setListingId(listing);
        chat1.setBuyerId(loggedInUser);
        chat2.setBuyerId(loggedInUser);
        chats.add(chat1);
        chats.add(chat2);
        lenient().when(chatService.findAllChatsByListing(listing)).thenReturn(chats);

        try {
            listingController.deleteListing(listingId, httpSession,model);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void testUpdateListing() throws Exception {

        String loggedInUserId = "testUserId";
        String listingId = "testListingId";

        ListingDto listingDto = new ListingDto();
        listingDto.setId(listingId);
        listingDto.setTitle("Updated Title");
        listingDto.setDescription("Updated Description");
        listingDto.setPrice(100L);

        Listing existingListing = new Listing();
        existingListing.setId(listingId);


        HttpSession httpSession = mock(HttpSession.class);
        when(httpSession.getAttribute("user_id")).thenReturn(loggedInUserId);

        when(listingService.findById(listingId)).thenReturn(existingListing);

        String result = listingController.updateListing(listingDto, httpSession, mock(Model.class));

        verify(listingService).saveListing(any(Listing.class));

        assertEquals("redirect:/mylistings", result);
    }
}
