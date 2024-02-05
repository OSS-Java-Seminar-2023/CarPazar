package hr.carpazar.controllers;

import hr.carpazar.dtos.SpecificationDto;
import hr.carpazar.models.Specification;
import hr.carpazar.services.ListingService;
import hr.carpazar.services.SpecificationService;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;
    @Autowired
    private ListingService listingService;

    @GetMapping(path = "/add-info")
    public String openSpecificationForm() {
        return "new_listing_info";
    }

    @PostMapping(path = "/add-info")
    public String addListingSpecs(
            @ModelAttribute SpecificationDto specificationDto, HttpSession httpSession, Model model) {
        String userid = (String) httpSession.getAttribute("user_id");
        if (userid == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        String listingid = (String) httpSession.getAttribute("listing_id");

        Specification specs = specificationService.createSpecificationFromDto(specificationDto, listingid);
        specificationService.publishSpecification(specs);

        return "redirect:/listingWithImages/" + listingid;
    }

    @GetMapping(path = "/editSpecification/{listingId}")
    public String editSpecification(@PathVariable String listingId, Model model, HttpSession httpSession) {
        String loggedInId =
                (httpSession.getAttribute("user_id") != null) ? (String) httpSession.getAttribute("user_id") : null;
        if (loggedInId == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }

        Optional<Specification> specificationOptional =
                Optional.ofNullable(specificationService.findByListingId(listingId));

        if (specificationOptional.isPresent()) {
            Specification specification = specificationOptional.get();
            model.addAttribute("specification", specification);
            return "editSpecification";
        } else {
            return "redirect:/notFound";
        }
    }

    @PostMapping(path = "/editSpecification/update")
    public String updateListing(@ModelAttribute SpecificationDto specificationDto, HttpSession session, Model model) {
        String loggedInId = (session.getAttribute("user_id") != null) ? (String) session.getAttribute("user_id") : null;
        if (loggedInId == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }

        String specId = specificationDto.getId();
        Optional<Specification> specificationOptional =
                Optional.ofNullable(specificationService.findByListingId(specId));

        if (specificationOptional.isPresent()) {
            Specification specs = specificationOptional.get();
            specificationService.updateSpecificationFromDto(specificationDto, specs);
            specificationService.publishSpecification(specs);

            return "redirect:/mylistings";
        } else {
            return "redirect:/notFound";
        }
    }

    @GetMapping(path = "/editAdditionalEquipment/{listingId}")
    public String editAdditionalEquipment(@PathVariable String listingId, Model model, HttpSession httpSession) {
        String loggedInId =
                (httpSession.getAttribute("user_id") != null) ? httpSession.getAttribute("user_id").toString() : null;
        if (loggedInId == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        Optional<Specification> specificationOptional =
                Optional.ofNullable(specificationService.findByListingId(listingId));
        if (specificationOptional.isPresent()) {
            Specification specification = specificationOptional.get();

            SpecificationDto specificationDto = new SpecificationDto();
            specificationDto.setId(specification.getId());
            specificationDto.setAdditionalEquipment(specification.getAdditionalEquipment());
            specificationDto.setExtraFeatures(specification.getExtraFeatures());

            model.addAttribute("specificationDto", specificationDto);
            model.addAttribute("extraFeaturesBin",
                    SpecificationService.checkboxToStringList(specification.getExtraFeatures(), "extraFeatures", false));
            model.addAttribute("additionalEquipmentBin",
                    SpecificationService.checkboxToStringList(
                            specification.getAdditionalEquipment(), "additionalEquipment", false));

            System.out.println(SpecificationService.checkboxToStringList(
                    specification.getAdditionalEquipment(), "additionalEquipment", false));

            return "editAdditionalEquipment";
        } else
            return "redirect:/notFound";
    }

    @PostMapping(path = "/editAdditionalEquipment/update")
    public String updateEquipment(
            @ModelAttribute SpecificationDto specificationDto, Model model, HttpSession httpSession) {
        String loggedInId =
                (httpSession.getAttribute("user_id") != null) ? httpSession.getAttribute("user_id").toString() : null;
        if (loggedInId == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }

        String specificationId = specificationDto.getId();
        Optional<Specification> specificationOptional =
                Optional.ofNullable(specificationService.findByListingId(specificationId));

        if (specificationOptional.isPresent()) {
            Specification specs = specificationOptional.get();
            specs.setAdditionalEquipment(specificationDto.getAdditionalEquipment());
            specs.setExtraFeatures(specificationDto.getExtraFeatures());

            specificationService.publishSpecification(specs);

            return "redirect:/listingWithImages/" + specs.getId();
        } else
            return "redirect:/notFound";
    }

    public SpecificationController(SpecificationService specificationService) {
        this.specificationService = specificationService;
    }
}