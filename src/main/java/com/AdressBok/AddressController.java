package com.AdressBok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping
    public String getIndex(Model model) {
        List<Address> addresses = (List<Address>) addressRepository.findAll();
        model.addAttribute("addresses", addresses);
        return "index";
    }

    @PostMapping("/new-address")
    public String addNew(@RequestParam("name") String name, @RequestParam("email") String email,
            @RequestParam("phone") String phone, @RequestParam("birthYear") int birthYear) {
        Address address = new Address();
        address.setName(name);
        address.setEmail(email);
        address.setPhone(phone);
        address.setBirthYear(birthYear);
        addressRepository.save(address);
        return "redirect:/";
    }

    @GetMapping("/delete-address")
    public String delete(@RequestParam int id) {
        addressRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/edit-address/{id}")
    public String edit(@PathVariable int id, Model model) {
        Address address = addressRepository.findById(id).orElse(null);
        if (address != null) {
            model.addAttribute("address", address);
            return "editAddress";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/update-address")
    public String update(@RequestParam int id, @RequestParam("name") String name,
            @RequestParam("email") String email, @RequestParam("phone") String phone,
            @RequestParam("birthYear") int birthYear) {
        Address existingAddress = addressRepository.findById(id).orElse(null);
        if (existingAddress != null) {
            existingAddress.setName(name);
            existingAddress.setEmail(email);
            existingAddress.setPhone(phone);
            existingAddress.setBirthYear(birthYear);
            addressRepository.save(existingAddress);
        }
        return "redirect:/";
    }

    @GetMapping("/addresses-between-years")
    public String findAddressesBetweenYears() {
        return "betweenYearsAddress";
    }

    @GetMapping("/between-years-address-result")
    public String showBetweenYearsAddressResult(@RequestParam("startYear") int startYear,
            @RequestParam("endYear") int endYear, Model model) {
        List<Address> addresses = (List<Address>) addressRepository.findAll();
        List<Address> addressesBetweenYears = addressRepository.findBetweenYears(startYear, endYear);
        model.addAttribute("addresses", addresses);
        model.addAttribute("addressesBetweenYears", addressesBetweenYears);
        model.addAttribute("startYear", startYear);
        model.addAttribute("endYear", endYear);
        return "index";
    }
}