package com.cg.onlinepizza;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cg.onlinepizza.coupon.dto.CouponDto;
import com.cg.onlinepizza.coupon.service.ICouponService;
import com.cg.onlinepizza.entity.Coupon;
import com.cg.onlinepizza.entity.Pizza;
import com.cg.onlinepizza.exceptions.CouponAlreadyExistException;
import com.cg.onlinepizza.exceptions.PizzaAlreadyExistException;
import com.cg.onlinepizza.pizza.dto.PizzaDto;
import com.cg.onlinepizza.pizza.service.IPizzaService;
import com.cg.onlinepizza.pizzaorder.dao.IPizzaOrderRepository;
import com.cg.onlinepizza.pizzaorder.service.IPizzaOrderService;
import com.cg.onlinepizza.secure.model.User;
import com.cg.onlinepizza.secure.service.JwtUserDetailsService;

@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private JwtUserDetailsService userDetailsService;
    
    @Autowired
    private IPizzaService pizzaService;
    
    @Autowired
    private ICouponService couponService;
    
    @Value("${admin.username}")
    private String adminusr;
    @Value("${admin.password}")
    private String adminpwd;
    @Value("${admin.role}")
    private String adminrole;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String autoCreate;
    
    @Override
    public void run(String... args) throws PizzaAlreadyExistException, CouponAlreadyExistException {
        if (autoCreate.equals("create")) {
            User user = new User(adminusr, adminpwd, adminrole);
            userDetailsService.save(user);
            
            Pizza pizza1 = new Pizza("Veg", "Farm House", "Regular", "Delightful combination of onion, capsicum, tomato & grilled mushroom.", 265);
            Pizza pizza2 = new Pizza("Veg", "Farm House", "Medium", "Delightful combination of onion, capsicum, tomato & grilled mushroom.", 469);
            Pizza pizza3 = new Pizza("Veg", "Farm House", "Large", "Delightful combination of onion, capsicum, tomato & grilled mushroom.", 705);
            Pizza pizza4 = new Pizza("Veg", "Peppy Paneer", "Regular", "Flavorful trio of juicy paneer, crisp capsicum with spicy red paprika.", 265);
            Pizza pizza5 = new Pizza("Veg", "Peppy Paneer", "Medium", "Flavorful trio of juicy paneer, crisp capsicum with spicy red paprika.", 469);
            Pizza pizza6 = new Pizza("Veg", "Peppy Paneer", "Large", "Flavorful trio of juicy paneer, crisp capsicum with spicy red paprika.", 705);
            Pizza pizza7 = new Pizza("Non-Veg", "Chicken Dominator", "Regular", "Loaded with double pepper barbecue chicken, peri-peri chicken, chicken tikka & grilled chicken rashers.", 365);
            Pizza pizza8 = new Pizza("Non-Veg", "Chicken Dominator", "Medium", "Loaded with double pepper barbecue chicken, peri-peri chicken, chicken tikka & grilled chicken rashers.", 609);
            Pizza pizza9 = new Pizza("Non-Veg", "Chicken Dominator", "Large", "Loaded with double pepper barbecue chicken, peri-peri chicken, chicken tikka & grilled chicken rashers.", 935);
            
            pizzaService.addPizza(entityToDto(pizza1));
            pizzaService.addPizza(entityToDto(pizza2));
            pizzaService.addPizza(entityToDto(pizza3));
            pizzaService.addPizza(entityToDto(pizza4));
            pizzaService.addPizza(entityToDto(pizza5));
            pizzaService.addPizza(entityToDto(pizza6));
            pizzaService.addPizza(entityToDto(pizza7));
            pizzaService.addPizza(entityToDto(pizza8));
            pizzaService.addPizza(entityToDto(pizza9));
            
            Coupon coupon1= new Coupon("PIZZA50","FLAT" ,50, 500, "Flat Rs. 50 off on a minimum order of Rs. 500");
            Coupon coupon2= new Coupon("ICC20", "PERCENTAGE",20,100, "20% OFF upto Rs. 100 on your orders.");
            couponService.addCoupons(entityToDto(coupon1));
            couponService.addCoupons(entityToDto(coupon2));
        }
    }
    
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	
	/*PizzaDto to Pizza Entity Class Conversion*/
	public Pizza dtoToEntity(PizzaDto pizza) {
		Pizza p = new Pizza();
		p.setPizzaId(pizza.getPizzaId());
		p.setPizzaName(pizza.getPizzaName());
		p.setPizzaType(pizza.getPizzaType());
		p.setPizzaSize(pizza.getPizzaSize());
		p.setPizzaDescription(pizza.getPizzaDescription());
		p.setPizzaCost(pizza.getPizzaCost());
		return p;
	}
	
	/*Pizza Entity to PizzaDto Class Conversion*/
	public PizzaDto entityToDto(Pizza pizza) {
		PizzaDto p = new PizzaDto();
		p.setPizzaId(pizza.getPizzaId());
		p.setPizzaName(pizza.getPizzaName());
		p.setPizzaType(pizza.getPizzaType());
		p.setPizzaSize(pizza.getPizzaSize());
		p.setPizzaDescription(pizza.getPizzaDescription());
		p.setPizzaCost(pizza.getPizzaCost());
		return p;
	}
	
	//Coupon Entity to CouponDto Class Conversion//
		public CouponDto entityToDto(Coupon coupon) {
			CouponDto c= new ModelMapper().map(coupon,CouponDto.class);
			return c;
		}
		/*CoupanDto to Coupon Entity Class Conversion*/
		public Coupon dtoToEntity(CouponDto coupon) {
			Coupon c= new ModelMapper().map(coupon,Coupon.class);
			return c;
		}
}
