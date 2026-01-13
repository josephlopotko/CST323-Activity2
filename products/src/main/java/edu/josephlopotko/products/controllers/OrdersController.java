package edu.josephlopotko.products.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.josephlopotko.products.data.OrdersDataService;
import edu.josephlopotko.products.models.OrderModel;
import jakarta.validation.Valid;





@Controller
@RequestMapping("/orders")
public class OrdersController {

    // add a data member for the repository using dependency injection
    @Autowired
    private OrdersDataService ordersDataService;

    // use constructor jinjection to inject the repository
    public OrdersController(OrdersDataService ordersDataService){
        this.ordersDataService = ordersDataService;
    }

    // methods to handle request endpoints


    // show all orders
    @GetMapping("")
    public String showAllOrders(Model model){
        model.addAttribute("orders", ordersDataService.getAll());
        model.addAttribute("title", "All Orders");
        return "allOrders";
    }

    // show an order
    @GetMapping("/showOrder/{id}")
    public String showOneOrder (@PathVariable("id") int id, Model model) {
        model.addAttribute("order", ordersDataService.getById(id));
        model.addAttribute("title", "One Order");

        return "oneOrder";
    }
    
    // edit order
    @GetMapping("/editOrder/{id}")
    public String editOrder(@PathVariable("id") int id, Model model){
        model.addAttribute("order", ordersDataService.getById(id));
        model.addAttribute("title", "Edit Order");

        return "editOrder";
    }

    // process edit
    @GetMapping("/processEditOrder")
    public String doUpdate(OrderModel orderModel, BindingResult br, Model model) {
        ordersDataService.update(orderModel);
        return "redirect:/orders";
    }
    
    // show new form
    @GetMapping("/newOrder")
    public String newOrder(Model model)
    {
        model.addAttribute("order", new OrderModel());
        model.addAttribute("title", "New Order");

        return "newOrder";
    }

    // process new order
    @GetMapping("/processNewOrder")
    public String processNewOrder(@Valid OrderModel orderModel, BindingResult br, Model model) {
        if (br.hasErrors()){
            model.addAttribute("title", "New Order");
            return "newOrder";
        }
        ordersDataService.create(orderModel);
        return "redirect:/orders";
    }
    
    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable("id") int id, Model model) {
        ordersDataService.deleteById(id);

        return "redirect:/orders";
    }
    
}
