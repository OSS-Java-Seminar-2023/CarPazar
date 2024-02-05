package hr.carpazar.controllerTests;

import hr.carpazar.controllers.ChatController;
import hr.carpazar.controllers.ListingController;
import hr.carpazar.models.Chat;
import hr.carpazar.models.Listing;
import hr.carpazar.models.User;
import hr.carpazar.services.ChatService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ChatTest {
    @Mock
    private ListingService listingService;

    @Mock
    private ChatService chatService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ChatController chatController;


    @Test
    void testStartChatWithSeller() {


        String loggedInUserId = "testUserId";
        String listingId = "testListingId";
        String sellerId = "testSellerId";
        String chatId = "testChatId";

        HttpSession httpSession = mock(HttpSession.class);
        when(httpSession.getAttribute("user_id")).thenReturn(loggedInUserId);

        User loggedInUser = new User();
        loggedInUser.setId(loggedInUserId);

        User seller = new User();
        seller.setId(sellerId);

        Listing listing = new Listing();
        listing.setId(listingId);

        when(listingService.findSellerByListingID(listingId)).thenReturn(seller);
        when(userService.findById(loggedInUserId)).thenReturn(loggedInUser);
        when(listingService.findById(listingId)).thenReturn(listing);

        Chat existingChat = new Chat();
        existingChat.setId(chatId);
        when(chatService.findExistingChat(loggedInUser, listing)).thenReturn(Optional.of(existingChat));

        RedirectAttributes redirectAttributes1 = mock(RedirectAttributes.class);
        Model model1 = mock(Model.class);
        String result1 = chatController.startChatWithSeller(listingId, httpSession, redirectAttributes1, model1);

        verify(redirectAttributes1).addAttribute("chatid", chatId);
        assertEquals("redirect:/chat/{chatid}", result1);


        when(chatService.findExistingChat(loggedInUser, listing)).thenReturn(Optional.empty());

        Chat newChat = new Chat();
        newChat.setId(chatId);
        when(chatService.saveChat(any(Chat.class))).thenReturn(newChat);

        RedirectAttributes redirectAttributes2 = mock(RedirectAttributes.class);
        Model model2 = mock(Model.class);
        String result2 = chatController.startChatWithSeller(listingId, httpSession, redirectAttributes2, model2);

        verify(redirectAttributes2).addAttribute("chatid", chatId);
        assertEquals("redirect:/chat/{chatid}", result2);
    }
}
