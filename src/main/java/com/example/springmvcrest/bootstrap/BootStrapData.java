package com.example.springmvcrest.bootstrap;


import com.example.springmvcrest.product.domain.*;
import com.example.springmvcrest.product.repository.AttributeRepository;
import com.example.springmvcrest.product.repository.CategoryRepository;
import com.example.springmvcrest.user.user.repository.RoleRepository;
import com.example.springmvcrest.user.simple.repository.SimpleUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class BootStrapData implements ApplicationListener<ContextRefreshedEvent> {
    private final SimpleUserRepository simpleUserRepository;
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository ;
    private final AttributeRepository attributeRepository;



    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        /*SimpleUser simpleUser =new SimpleUser();
        simpleUser.setEmail("email");
        simpleUser.setUserName("username");
        simpleUser.setPassWord("password");

        Role role=new Role();
        role.setName(RoleContext.USER);
        role.getUsers().add(simpleUser);
        role=roleRepository.save(role);

        simpleUserRepository.save(simpleUser);
        simpleUser.getRoles().add(roleRepository.findById(role.getId()).get());*/



        /*if(productRepository.count()==0){
            productRepository.saveAll(getProducts());

            System.out.println("Loading customer Data");

           /* User c1= new User();
            c1.setEmail("a@a.com");
            c1.setUserName("ibrahim");
            c1.setPassWord1("123");
            c1.setPassWord2("123");
            userRepository.save(c1);*/


         /*   System.out.println("Customer saved: "+userRepository.count());
        }*/




    }


    private List<Product> getProducts(){
        List<Product> products = new ArrayList<>(1);


        String BASE_URL = "http://192.168.1.37:8085/api/v1/";



        /* *********Electronics************/

        Category Electronics=new Category("Electronics");
        categoryRepository.save(Electronics);

        Category AccessoriesSupplies=new Category("AccessoriesSupplies");
        AccessoriesSupplies.setParenetId(Electronics.getId());
        categoryRepository.save(AccessoriesSupplies);

        Category CameraPhoto=new Category("CameraPhoto");
        CameraPhoto.setParenetId(Electronics.getId());
        categoryRepository.save(CameraPhoto);

        Category CarVehicleElectronics=new Category("CarVehicleElectronics");
        CarVehicleElectronics.setParenetId(Electronics.getId());
        categoryRepository.save(CarVehicleElectronics);

        Category CellPhonesAccessories=new Category("CellPhonesAccessories");
        CellPhonesAccessories.setParenetId(Electronics.getId());
        categoryRepository.save(CellPhonesAccessories);

        Category ComputersAccessories=new Category("ComputersAccessories");
        ComputersAccessories.setParenetId(Electronics.getId());
        categoryRepository.save(ComputersAccessories);

        Category Headphones=new Category("Headphones");
        Headphones.setParenetId(Electronics.getId());
        categoryRepository.save(Headphones);

        /* *********Computers************/
        Category Computers=new Category("Computers");
        categoryRepository.save(Computers);

        Category ComputerAccessoriesPeripherals=new Category("ComputerAccessoriesPeripherals");
        ComputerAccessoriesPeripherals.setParenetId(Computers.getId());
        categoryRepository.save(ComputerAccessoriesPeripherals);

        Category ComputerComponents=new Category("ComputerComponents");
        ComputerComponents.setParenetId(Computers.getId());
        categoryRepository.save(ComputerComponents);

        Category DataStorage=new Category("DataStorage");
        DataStorage.setParenetId(Computers.getId());
        categoryRepository.save(DataStorage);

        Category ExternalComponents=new Category("ExternalComponents");
        ExternalComponents.setParenetId(Computers.getId());
        categoryRepository.save(ExternalComponents);

        Category Laptop=new Category("Laptop");
        Laptop.setParenetId(Computers.getId());
        categoryRepository.save(Laptop);

        /* *********WomensFashion************/

        Category WomensFashion=new Category("WomensFashion");
        categoryRepository.save(WomensFashion);

        Category Clothing=new Category("ClothingW");
        Clothing.setParenetId(WomensFashion.getId());
        categoryRepository.save(Clothing);

        Category Shoes=new Category("ShoesW");
        Shoes.setParenetId(WomensFashion.getId());
        categoryRepository.save(Shoes);

        Category Jewelry=new Category("JewelryW");
        Jewelry.setParenetId(WomensFashion.getId());
        categoryRepository.save(Jewelry);

        Category Watches=new Category("WatchesW");
        Watches.setParenetId(WomensFashion.getId());
        categoryRepository.save(Watches);

        /* *********MensFashion************/

        Category MensFashion=new Category("MensFashion");
        categoryRepository.save(MensFashion);

        Category ClothingM=new Category("ClothingM");
        ClothingM.setParenetId(MensFashion.getId());
        categoryRepository.save(ClothingM);

        Category ShoesM=new Category("ShoesM");
        ShoesM.setParenetId(MensFashion.getId());
        categoryRepository.save(ShoesM);

        Category AccessoriesM=new Category("AccessoriesM");
        AccessoriesM.setParenetId(MensFashion.getId());
        categoryRepository.save(AccessoriesM);

        Category WatchesM=new Category("WatchesM");
        WatchesM.setParenetId(MensFashion.getId());
        categoryRepository.save(WatchesM);

        /* *********GirlsFashion************/
        Category GirlsFashion=new Category("GirlsFashion");
        categoryRepository.save(GirlsFashion);

        Category ClothingG=new Category("ClothingG");
        ClothingG.setParenetId(GirlsFashion.getId());
        categoryRepository.save(ClothingG);

        Category ShoesG=new Category("ShoesG");
        ShoesG.setParenetId(GirlsFashion.getId());
        categoryRepository.save(ShoesG);

        Category JewelryG=new Category("JewelryG");
        JewelryG.setParenetId(GirlsFashion.getId());
        categoryRepository.save(JewelryG);

        Category WatchesG=new Category("WatchesG");
        WatchesG.setParenetId(GirlsFashion.getId());
        categoryRepository.save(WatchesG);

        /* *********BoysFashion************/
        Category BoysFashion=new Category("BoysFashion");
        categoryRepository.save(BoysFashion);

        Category ClothingB=new Category("ClothingB");
        ClothingB.setParenetId(BoysFashion.getId());
        categoryRepository.save(ClothingB);

        Category ShoesB=new Category("ShoesB");
        ShoesB.setParenetId(BoysFashion.getId());
        categoryRepository.save(ShoesB);

        Category AccessoriesB=new Category("AccessoriesB");
        AccessoriesB.setParenetId(BoysFashion.getId());
        categoryRepository.save(AccessoriesB);

        Category WatchesB=new Category("WatchesB");
        WatchesB.setParenetId(BoysFashion.getId());
        categoryRepository.save(WatchesB);
        /* *********SportsandOutdoors************/
        Category SportsandOutdoors=new Category("SportsandOutdoors");
        categoryRepository.save(SportsandOutdoors);

        Category SportsAndOutdoors=new Category("SportsAndOutdoors");
        SportsAndOutdoors.setParenetId(SportsandOutdoors.getId());
        categoryRepository.save(SportsAndOutdoors);

        Category SportsFitness=new Category("SportsFitness");
        SportsFitness.setParenetId(SportsandOutdoors.getId());
        categoryRepository.save(SportsFitness);


        Product dell=new Product();
        dell.setName("Dell Inspiron 15 3000 15.6\" HD LED-Backlit Screen");
        dell.setDescription("15.6\" HD Energy-efficient LED-backlit (1366 x 768) Display, AMD Athlon Silver 3050U up to 3.2GHz, Integrated graphics with AMD 4GB high-bandwidth RAM to smoothly run multiple applications and browser tabs all at once; 128GB NVMe SSD allows fast bootup and data transfer. 1x USB 2.0 port, 2x USB 3.2 Gen 1 ports, 1x Headset (headphone and microphone combo) port, 1x RJ45 - 10/100Mbps Ethernet port, 1x HDMI 1.4 port, 1x Power-adapter port, 1 x TWE Mouse Pad");
        dell.getImages().add(new Images("https://images-na.ssl-images-amazon.com/images/I/4184m0GW8AL._AC_.jpg",dell));
        dell.getImages().add(new Images("https://images-na.ssl-images-amazon.com/images/I/31eJ7soz%2BYL._AC_.jpg",dell));
        dell.getImages().add(new Images("https://images-na.ssl-images-amazon.com/images/I/41Pc2mBwlOL._AC_.jpg",dell));
        dell.getCategories().add(AccessoriesSupplies);
        dell.getTags().add(new Tags("dell",dell));
        dell.getProductVariants().add(new ProductVariant(dell,20.0,5));

        products.add(dell);


        Product shirt=new Product();
        shirt.setName("Organic Signatures Men's Short-Sleeve Crewneck 100% Organic Cotton T-Shirt");
        shirt.setDescription("100% COTTON T-SHIRTS FOR MEN – ECO-FRIENDLY, COOL & COMFORTABLE – Spruce up your eco-conscious attire with the perfect mix of casual style and trendy organic. Fashionable, contemporary, featuring a tagless neckline, our men’s short sleeve t-shirts are made from the purest, high-quality cotton for an exceptional handfeel. Enjoy a comfortable & relaxed fit with extra peace of mind – our cotton is farm-grown without pesticides or chemical fertilizers. 100% safe for your skin AND for the planet!");
        shirt.getImages().add(new Images("https://images-na.ssl-images-amazon.com/images/I/81-%2B2YdVqCL._AC_UX466_.jpg",shirt));
        shirt.getImages().add(new Images("https://m.media-amazon.com/images/I/81eLanBH+kL._AC_SX466._SX._UX._SY._UY_.jpg",shirt));
        shirt.getCategories().add(AccessoriesSupplies);
        shirt.getTags().add(new Tags("shirt",shirt));


        Attribute color=new Attribute();
        color.setName("color");
        AttributeValue red =new AttributeValue(color);
        red.setValue("red");
        red.setImage("https://images-na.ssl-images-amazon.com/images/I/81uzH-WDhoL._AC_UX466_.jpg");

        AttributeValue grey =new AttributeValue(color);
        grey.setValue("grey");
        grey.setImage("https://images-na.ssl-images-amazon.com/images/I/91P5kRomY5L._AC_UX466_.jpg");
        color.getAttributeValues().add(red);
        color.getAttributeValues().add(grey);

        Attribute size=new Attribute();
        size.setName("size");
        AttributeValue small =new AttributeValue(size);
        small.setValue("small");
        small.setImage("null");

        AttributeValue large =new AttributeValue(size);
        large.setValue("large");
        large.setImage("null");
        size.getAttributeValues().add(small);
        size.getAttributeValues().add(large);


        ProductVariant shirt_red_small=new ProductVariant();
        shirt_red_small.setPrice(20.0);
        shirt_red_small.setUnit(5);
        shirt_red_small.setProduct(shirt);
        shirt_red_small.getAttributeValues().add(red);
        shirt_red_small.getAttributeValues().add(small);

        red.getProductVariants().add(shirt_red_small);
        small.getProductVariants().add(shirt_red_small);

        ProductVariant shirt_red_large=new ProductVariant();
        shirt_red_large.setPrice(25.0);
        shirt_red_large.setUnit(3);
        shirt_red_large.setProduct(shirt);
        shirt_red_large.getAttributeValues().add(red);
        shirt_red_large.getAttributeValues().add(large);

        red.getProductVariants().add(shirt_red_large);
        large.getProductVariants().add(shirt_red_large);

        ProductVariant shirt_grey_small=new ProductVariant();
        shirt_grey_small.setPrice(25.0);
        shirt_grey_small.setUnit(3);
        shirt_grey_small.setProduct(shirt);
        shirt_grey_small.getAttributeValues().add(grey);
        shirt_grey_small.getAttributeValues().add(small);

        grey.getProductVariants().add(shirt_grey_small);
        small.getProductVariants().add(shirt_grey_small);

        shirt.getProductVariants().add(shirt_red_small);
        shirt.getProductVariants().add(shirt_red_large);
        shirt.getProductVariants().add(shirt_grey_small);

        attributeRepository.save(size);
        attributeRepository.save(color);


        products.add(shirt);


        Product ssd=new Product();
        ssd.setName("Samsung SSD 860 EVO 1TB 2.5 Inch SATA III Internal SSD (MZ-76E1T0B/AM)");
        ssd.setDescription("Innovative V-Nand Technology: Powered by Samsung V-Nand Technology, the 860 Evo SSD offers optimized performance for everyday computing as well as rendering large-sized 4K videos and 3D data used by the latest applications");
        ssd.getImages().add(new Images("https://images-na.ssl-images-amazon.com/images/I/91JA5-hAnoL._AC_SX569_.jpg",ssd));
        ssd.getImages().add(new Images("https://images-na.ssl-images-amazon.com/images/I/91uXmOzjQNL._AC_SX569_.jpg",ssd));
        ssd.getCategories().add(AccessoriesSupplies);
        ssd.getTags().add(new Tags("ssd",ssd));


        Attribute storageCapcity=new Attribute();
        storageCapcity.setName("storage Capcity");
        AttributeValue _256 =new AttributeValue(storageCapcity);
        _256.setValue("256");
        _256.setImage("null");
        AttributeValue _1tb =new AttributeValue(storageCapcity);
        _1tb.setValue("1 TB");
        _1tb.setImage("null");
        AttributeValue _512 =new AttributeValue(storageCapcity);
        _512.setValue("512");
        _512.setImage("null");

        storageCapcity.getAttributeValues().add(_256);
        storageCapcity.getAttributeValues().add(_1tb);
        storageCapcity.getAttributeValues().add(_512);


        ProductVariant ssd_256=new ProductVariant();
        ssd_256.setPrice(20.0);
        ssd_256.setUnit(5);
        ssd_256.setProduct(ssd);
        ssd_256.getAttributeValues().add(_256);

        _256.getProductVariants().add(ssd_256);

        ProductVariant ssd_512=new ProductVariant();
        ssd_512.setPrice(30.0);
        ssd_512.setUnit(10);
        ssd_512.setProduct(ssd);
        ssd_512.getAttributeValues().add(_512);

        _512.getProductVariants().add(ssd_512);

        ProductVariant ssd_1tb=new ProductVariant();
        ssd_1tb.setPrice(35.0);
        ssd_1tb.setUnit(50);
        ssd_1tb.setProduct(ssd);
        ssd_1tb.getAttributeValues().add(_1tb);

        _1tb.getProductVariants().add(ssd_1tb);


        ssd.getProductVariants().add(ssd_256);
        ssd.getProductVariants().add(ssd_512);
        ssd.getProductVariants().add(ssd_1tb);

        attributeRepository.save(storageCapcity);

        products.add(ssd);







/*

        Product p1c1=new Product();
        p1c1.setDescription("Please note: \n1. The light stand is packed in a separate box inside the big package box (under the ring light box at the extreme bottom of the box)as shown in the 2nd picture. If you can't find it, please contact seller customer service;\n 2. Instructions for unfolding the stand are in the 8th picture. The legs are upwards, so please unfold the legs upside down");
        p1c1.setName("Neewer Ring Light Kit:18\"/48cm Outer 55W 5500K Dimmable LED Ring Light, Light Stand, Carrying Bag for Camera,Smartphone,YouTube,TikTok,Self-Portrait Shooting, Black, Model:10088612");
        p1c1.setPrice(84.99);
        p1c1.setUnit(10);
        p1c1.setOrigin("china");
        p1c1.setImage(BASE_URL+"user/image/p1c1.jpg");
        p1c1.getCategories().add(CameraPhoto);
        p1c1.getCategories().add(categoryRepository.findById(CameraPhoto.getParenetId()).get());

        products.add(p1c1);

        Product subp1c1=new Product();
        subp1c1.setDescription(p1c1.getDescription());
        subp1c1.setName(p1c1.getName());
        subp1c1.setPrice(p1c1.getPrice());
        subp1c1.setUnit(2);
        subp1c1.setOrigin(p1c1.getOrigin());
        subp1c1.setImage(p1c1.getImage());
        subp1c1.setCategories(p1c1.getCategories());
        subp1c1.getColors().add(blue);
        subp1c1.getDimensions().add(xl);
        subp1c1.setParentProduct(p1c1);

        products.add(subp1c1);

        Product sub1p1c1=new Product();
        sub1p1c1.setDescription(p1c1.getDescription());
        sub1p1c1.setName(p1c1.getName());
        sub1p1c1.setPrice(p1c1.getPrice());
        sub1p1c1.setUnit(3);
        sub1p1c1.setOrigin(p1c1.getOrigin());
        sub1p1c1.setImage(p1c1.getImage());
        sub1p1c1.setCategories(p1c1.getCategories());
        sub1p1c1.getColors().add(yellow);
        sub1p1c1.getDimensions().add(s);
        sub1p1c1.setParentProduct(p1c1);

        products.add(sub1p1c1);

        Product sub2p1c1=new Product();
        sub2p1c1.setDescription(p1c1.getDescription());
        sub2p1c1.setName(p1c1.getName());
        sub2p1c1.setPrice(p1c1.getPrice());
        sub2p1c1.setUnit(20);
        sub2p1c1.setOrigin(p1c1.getOrigin());
        sub2p1c1.setImage(p1c1.getImage());
        sub2p1c1.setCategories(p1c1.getCategories());
        sub2p1c1.getColors().add(yellow);
        sub2p1c1.getDimensions().add(m);
        sub2p1c1.setParentProduct(p1c1);

        products.add(sub2p1c1);

        Product p2c1=new Product();
        p2c1.setDescription("Incredible speeds in a microSD card officially licensed for the Nintendo Switch and Nintendo Switch Lite systems");
        p2c1.setName("SanDisk 128GB microSDXC UHS-I-Memory-Card for Nintendo-Switch - SDSQXAO-128G-GNCZN");
        p2c1.setPrice(23.99);
        p2c1.setUnit(10);
        p2c1.setOrigin("usa");
        p2c1.setImage(BASE_URL+"user/image/p2c1.jpg");
        p2c1.getCategories().add(ComputersAccessories);
        p2c1.getCategories().add(categoryRepository.findById(ComputersAccessories.getParenetId()).get());

        products.add(p2c1);

        Product p3c1=new Product();
        p3c1.setDescription("Easy One Touch Lock/Release: Patented easy one touch mechanism allows quick one hand open and close operation");
        p3c1.setName("iOttie Easy One Touch 4 Dash & Windshield Car Mount Phone Holder Desk Stand Pad & Mat for iPhone, Samsung, Moto, Huawei, Nokia, LG, Smartphones");
        p3c1.setPrice(24.95);
        p3c1.setUnit(10);
        p3c1.setOrigin("china");
        p3c1.setColor("Black");
        p3c1.setFabricType("Faux Leather");
        p3c1.setSize("3 x 5 x 9 inches");
        p3c1.getCategories().add(CellPhonesAccessories);
        p3c1.getCategories().add(categoryRepository.findById(CellPhonesAccessories.getParenetId()).get());
        p3c1.setImage(BASE_URL+"user/image/p3c1.jpg");

        products.add(p3c1);

        Product p4c1=new Product();
        p4c1.setDescription("【Dual 1080P dash cam】The front and rear camera simultaneously capture the road front (155°) and rear (126°) in crystal details at Dual 1920x1080p 30fps. Or single front Camera provides you with a crystal clear QHD 2560x1440p@30fps video resolution.");
        p4c1.setName("Dual Dash cam | VAVA Dual 1920x1080P FHD | Front and Rear dash camera | 2560x1440P Single Front| for cars with Wi-Fi | Night Vision | Parking Mode | G-sensor | WDR | Loop recording| Support 128GB Max");
        p4c1.setPrice(149.99);
        p4c1.setUnit(10);
        p4c1.setOrigin("china");
        p4c1.setColor("Black");
        p4c1.setSize("7.72 x 2.83 x 7.32 inches");
        p4c1.getCategories().add(CarVehicleElectronics);
        p4c1.getCategories().add(categoryRepository.findById(CarVehicleElectronics.getParenetId()).get());
        p4c1.setImage(BASE_URL+"user/image/p4c1.jpg");

        products.add(p4c1);

        Product p5c1=new Product();
        p5c1.setDescription("Three levels of world-class noise cancellation for better listening experience in any environment");
        p5c1.setName("Bose QuietComfort 35 II Wireless Bluetooth Headphones, Noise-Cancelling, with Alexa voice control - Black");
        p5c1.setPrice(269.00);
        p5c1.setUnit(10);
        p5c1.setOrigin("china");
        p5c1.setColor("Black");
        p5c1.getCategories().add(Headphones);
        p5c1.getCategories().add(categoryRepository.findById(Headphones.getParenetId()).get());
        p5c1.getCategories().add(ComputersAccessories);
        p5c1.getCategories().add(categoryRepository.findById(ComputersAccessories.getParenetId()).get());
        p5c1.setImage(BASE_URL+"user/image/p5c1.jpg");
        products.add(p5c1);

        Product p6c1=new Product();
        p6c1.setDescription("Exactly what you want to hear. Galaxy Buds+ are the perfect fitting earbuds to isolate you from distracting noises, so you can stay focused on what you want. And if you’d like to filter in some of the outside world to be more in-tune with your surroundings, you can switch on Ambient Aware2. Hear flight annozments, oncoming traffic or your order number while still enjoying your favorite playlist or podcast.");
        p6c1.setName("Samsung Galaxy Buds+ Plus, True Wireless Earbuds (Wireless Charging Case Included), Black – US Version");
        p6c1.setPrice(109.99);
        p6c1.setUnit(10);
        p6c1.setOrigin("china");
        p6c1.setColor("Black");
        p6c1.getCategories().add(Headphones);
        p6c1.getCategories().add(categoryRepository.findById(Headphones.getParenetId()).get());
        p6c1.getCategories().add(CellPhonesAccessories);
        p6c1.getCategories().add(categoryRepository.findById(CellPhonesAccessories.getParenetId()).get());
        p6c1.setImage(BASE_URL+"user/image/p6c1.jpg");

        products.add(p6c1);

        Product p7c1=new Product();
        p7c1.setDescription("Make sure this fits by entering your model number.\n" +
                "Innovative V-Nand Technology: Powered by Samsung V-Nand Technology, the 860 Evo SSD offers optimized performance for everyday computing as well as rendering large-sized 4K videos and 3D data used by the latest applications");
        p7c1.setName("Samsung SSD 860 EVO 1TB 2.5 Inch SATA III Internal SSD (MZ-76E1T0B/AM)");
        p7c1.setPrice(99.99);
        p7c1.setUnit(10);
        p7c1.setOrigin("china");
        p7c1.getCategories().add(ComputersAccessories);
        p7c1.getCategories().add(categoryRepository.findById(ComputersAccessories.getParenetId()).get());
        p7c1.getCategories().add(ComputerAccessoriesPeripherals);
        p7c1.getCategories().add(categoryRepository.findById(ComputerAccessoriesPeripherals.getParenetId()).get());
        p7c1.getCategories().add(ComputerComponents);
        p7c1.getCategories().add(categoryRepository.findById(ComputerComponents.getParenetId()).get());
        p7c1.getCategories().add(DataStorage);
        p7c1.getCategories().add(categoryRepository.findById(DataStorage.getParenetId()).get());
        p7c1.setImage(BASE_URL+"user/image/p7c1.jpg");
        p7c1.getTags().add(new Tags("ssd 1tb",p7c1));
        p7c1.getTags().add(new Tags("Samsung evo",p7c1));
        p7c1.getTags().add(new Tags("ssd",p7c1));
        products.add(p7c1);

        Product p8c1=new Product();
        p8c1.setDescription("[TWS & BLUETOOTH 5. 0] - Adopt the most advanced Bluetooth 5. 0 technology. TOZO T6 Support HSP, HFP, A2DP, AVRCP. Provides in-call stereo sound. Also own fast and stable transmission without tangling.");
        p8c1.setName("TOZO T6 True Wireless Earbuds Bluetooth Headphones Touch Control with Wireless Charging Case IPX8 Waterproof TWS Stereo Earphones in-Ear Built-in Mic Headset Premium Deep Bass for Sport Black");
        p8c1.setPrice(39.99);
        p8c1.setUnit(10);
        p8c1.setOrigin("china");
        p8c1.setColor("Black");
        p8c1.getCategories().add(Headphones);
        p8c1.getCategories().add(categoryRepository.findById(Headphones.getParenetId()).get());
        p8c1.getCategories().add(CellPhonesAccessories);
        p8c1.getCategories().add(categoryRepository.findById(CellPhonesAccessories.getParenetId()).get());
        p8c1.setImage(BASE_URL+"user/image/p8c1.jpg");

        products.add(p8c1);
        //----------------------------------------------------------------------------
        Product p1c2=new Product();
        p1c2.setDescription("The world's most advanced processor in the desktop PC gaming segment\n" +
                "Can deliver ultra-fast 100+ FPS performance in the world's most popular games");
        p1c2.setName("AMD Ryzen 7 3700X 8-Core, 16-Thread Unlocked Desktop Processor with Wraith Prism LED Cooler");
        p1c2.setPrice(329.99);
        p1c2.setUnit(10);
        p1c2.setOrigin("usa");
        p1c2.getCategories().add(ComputersAccessories);
        p1c2.getCategories().add(categoryRepository.findById(ComputersAccessories.getParenetId()).get());
        p1c2.getCategories().add(ExternalComponents);
        p1c2.getCategories().add(categoryRepository.findById(ExternalComponents.getParenetId()).get());
        p1c2.getCategories().add(ComputerComponents);
        p1c2.getCategories().add(categoryRepository.findById(ComputerComponents.getParenetId()).get());
        p1c2.setImage(BASE_URL+"user/image/p1c2.jpg");
        products.add(p1c2);

        Product p2c2=new Product();
        p2c2.setDescription("Make sure this fits by entering your model number.\n" +
                "Hand-sorted memory chips ensure high performance with generous overclocking headroom.\n" +
                "Vengeance LPX is optimized for wide compatibility with the latest Intel and AMD DDR4 ram motherboards.\n" +
                "A low-profile height of just 34mm ensures that vengeance LPX even fits in most small-form-factor builds.");
        p2c2.setName("Corsair Vengeance LPX 16GB (2 X 8GB) DDR4 3600 (PC4-28800) C18 1.35V Desktop Memory - Black");
        p2c2.setPrice(70.99);
        p2c2.setUnit(10);
        p2c2.setOrigin("usa");
        p2c2.getCategories().add(ComputersAccessories);
        p2c2.getCategories().add(categoryRepository.findById(ComputersAccessories.getParenetId()).get());
        p2c2.getCategories().add(ExternalComponents);
        p2c2.getCategories().add(categoryRepository.findById(ExternalComponents.getParenetId()).get());
        p2c2.getCategories().add(ComputerComponents);
        p2c2.getCategories().add(categoryRepository.findById(ComputerComponents.getParenetId()).get());
        p2c2.setImage(BASE_URL+"user/image/p2c2.jpg");

        products.add(p2c2);

        Product p3c2=new Product();
        p3c2.setDescription("AMD Ryzen 3 3200U Dual Core Processor (Up to 3.5GHz); 4GB DDR4 Memory; 128GB PCIe NVMe SSD\n" +
                "15.6 inches full HD (1920 x 1080) widescreen LED backlit IPS display; AMD Radeon Vega 3 Mobile Graphics");
        p3c2.setName("Acer Aspire 5 Slim Laptop, 15.6 inches Full HD IPS Display, AMD Ryzen 3 3200U, Vega 3 Graphics, 4GB DDR4, 128GB SSD, Backlit Keyboard, Windows 10 in S Mode, A515-43-R19L, Silver");
        p3c2.setPrice(450.00);
        p3c2.setUnit(10);
        p3c2.setOrigin("china");
        p3c2.setImage(BASE_URL+"user/image/p3c2.jpg");
        p3c2.getCategories().add(Laptop);
        p3c2.getCategories().add(categoryRepository.findById(Laptop.getParenetId()).get());
        products.add(p3c2);

        Product p4c2=new Product();
        p4c2.setDescription("SLIM, STYLISH DESIGN: Stream and browse on a 10.4-inch¹ ultra-widescreen display designed to bring your content to life without weighing you down. The front-facing, landscape-oriented camera allows you to transition between entertainment and video calls seamlessly.");
        p4c2.setName("Samsung Galaxy Tab A7 10.4 Wi-Fi 32GB Silver (SM-T500NZSAXAR)");
        p4c2.setPrice(150.00);
        p4c2.setUnit(10);
        p4c2.setOrigin("china");
        p4c2.getCategories().add(ComputersAccessories);
        p4c2.getCategories().add(categoryRepository.findById(ComputersAccessories.getParenetId()).get());
        p4c2.setImage(BASE_URL+"user/image/p4c2.jpg");

        products.add(p4c2);

        Product p5c2=new Product();
        p5c2.setDescription("INNOVATIVE V NAND TECHNOLOGY: Powered by Samsung V NAND Technology, the 970 EVO SSD’s NVMe interface (PCIe M.2 2280) offers enhanced bandwidth, low latency, and power efficiency ideal for tech enthusiasts, high end gamers, and 4K & 3D content designers");
        p5c2.setName("Samsung (MZ-V7E1T0BW) 970 EVO SSD 1TB - M.2 NVMe Interface Internal Solid State Drive with V-NAND Technology");
        p5c2.setPrice(129.99);
        p5c2.setUnit(10);
        p5c2.setOrigin("china");
        p5c2.getCategories().add(ComputersAccessories);
        p5c2.getCategories().add(categoryRepository.findById(ComputersAccessories.getParenetId()).get());
        p5c2.getCategories().add(ComputerAccessoriesPeripherals);
        p5c2.getCategories().add(categoryRepository.findById(ComputerAccessoriesPeripherals.getParenetId()).get());
        p5c2.getCategories().add(ComputerComponents);
        p5c2.getCategories().add(categoryRepository.findById(ComputerComponents.getParenetId()).get());
        p5c2.getCategories().add(DataStorage);
        p5c2.getCategories().add(categoryRepository.findById(DataStorage.getParenetId()).get());
        p5c2.setImage(BASE_URL+"user/image/p5c2.jpg");
        p5c2.getTags().add(new Tags("ssd 1tb",p5c2));
        p5c2.getTags().add(new Tags("Samsung evo",p5c2));
        p5c2.getTags().add(new Tags("ssd",p5c2));
        products.add(p5c2);

        //--------------------------------------------------------------------------
        Product p1c3=new Product();
        p1c3.setDescription("VERSATILE FIT. Perfect for layering in the winter or during cool fall evenings, this relaxed fit flannel doesn't restrict movement so you can keep warm and keep moving on those colder days.");
        p1c3.setName("Wrangler Authentics Men's Long Sleeve Heavy Weight Fleece Shirt");
        p1c3.setPrice(19.36);
        p1c3.setUnit(10);
        p1c3.setOrigin("china");
        p1c3.setColor("Red Buffalo Plaid");
        p1c3.setFabricType("Polyester");
        p1c3.getCategories().add(ClothingM);
        p1c3.getCategories().add(categoryRepository.findById(ClothingM.getParenetId()).get());
        p1c3.setImage(BASE_URL+"user/image/p1c3.jpg");
        p1c3.getTags().add(new Tags("Shirt mens",p1c3));
        p1c3.getTags().add(new Tags("Shirt men",p1c3));
        p1c3.getTags().add(new Tags("Shirt men's",p1c3));
        products.add(p1c3);

        Product p2c3=new Product();
        p2c3.setDescription("Legendary Signature Buck applique\n" +
                "Double needle stitching and hand warmer side pockets\n" +
                "The sizing of this item is built larger for extra layering. We recommend ordering a size down for a closer fit to your regular size.");
        p2c3.setName("Legendary Whitetails Men's Journeyman Flannel Lined Rugged Shirt Jacket");
        p2c3.setPrice(77.8);
        p2c3.setUnit(10);
        p2c3.setOrigin("china");
        p2c3.setColor("Dark Army");
        p2c3.setFabricType("63% Cotton, 37% Polyester");
        p2c3.getCategories().add(ClothingM);
        p2c3.getCategories().add(categoryRepository.findById(ClothingM.getParenetId()).get());
        p2c3.setImage(BASE_URL+"user/image/p2c3.jpg");
        p2c3.getTags().add(new Tags("Shirt mens",p2c3));
        p2c3.getTags().add(new Tags("Shirt men",p2c3));
        p2c3.getTags().add(new Tags("Shirt men's",p2c3));
        products.add(p2c3);

        Product p4c3=new Product();
        p4c3.setDescription("Shaft measures approximately low-top from arch\n" +
                "NEUTRAL: For runners who need a balance of flexibility & cushioning\n" +
                "Lightweight mesh upper with 3-color digital print delivers complete breathability");
        p4c3.setName("Under Armour Men's Charged Assert 8 Running Shoe");
        p4c3.setPrice(70.00);
        p4c3.setUnit(10);
        p4c3.setOrigin("china");
        p4c3.setColor("Red");
        p4c3.setFabricType("84% Polyester, 16% Elastane");
        p4c3.getCategories().add(ShoesM);
        p4c3.getCategories().add(categoryRepository.findById(ShoesM.getParenetId()).get());
        p4c3.getCategories().add(SportsAndOutdoors);
        p4c3.getCategories().add(categoryRepository.findById(SportsAndOutdoors.getParenetId()).get());
        p4c3.setImage(BASE_URL+"user/image/p4c3.jpg");
        p4c3.getTags().add(new Tags("shoe mens",p4c3));
        p4c3.getTags().add(new Tags("shoe men",p4c3));
        p4c3.getTags().add(new Tags("shoe men's",p4c3));
        products.add(p4c3);

        Product p5c3=new Product();
        p5c3.setDescription("Shaft measures approximately 4\" from arch" +
                "Uggpure wool insole" +
                "Outsole is low profile eva for comfort" +
                "Fully lined with uggpure wool" +
                "Wear indoors as a slipper or out with slim jeans and a V-neck  shoe" );
        p5c3.setName("UGG Men's Neumel Chukka Boot");
        p5c3.setPrice(89.88);
        p5c3.setUnit(10);
        p5c3.setOrigin("china");
        p5c3.setColor("Chestnut");
        p5c3.getCategories().add(ShoesM);
        p5c3.getCategories().add(categoryRepository.findById(ShoesM.getParenetId()).get());
        p5c3.setImage(BASE_URL+"user/image/p5c3.jpg");
        p5c3.getTags().add(new Tags("shoe mens",p5c3));
        p5c3.getTags().add(new Tags("shoe men",p5c3));
        p5c3.getTags().add(new Tags("shoe men's",p5c3));
        p5c3.getColors().add(yellow);
        p5c3.getDimensions().add(shoe37);
        products.add(p5c3);

        Product sub1p5c3=new Product();
        sub1p5c3.setDescription(p5c3.getDescription());
        sub1p5c3.setName(p5c3.getName());
        sub1p5c3.setPrice(p5c3.getPrice());
        sub1p5c3.setUnit(2);
        sub1p5c3.setOrigin(p5c3.getOrigin());
        sub1p5c3.setImage(BASE_URL+"user/image/sub1p5c3.jpg");
        sub1p5c3.setCategories(p5c3.getCategories());
        sub1p5c3.getColors().add(blue);
        sub1p5c3.getDimensions().add(shoe37);
        sub1p5c3.setParentProduct(p5c3);

        products.add(sub1p5c3);

        Product sub2p5c3=new Product();
        sub2p5c3.setDescription(p5c3.getDescription());
        sub2p5c3.setName(p5c3.getName());
        sub2p5c3.setPrice(p5c3.getPrice());
        sub2p5c3.setUnit(2);
        sub2p5c3.setOrigin(p5c3.getOrigin());
        sub2p5c3.setImage(BASE_URL+"user/image/sub1p5c3.jpg");
        sub2p5c3.setCategories(p5c3.getCategories());
        sub2p5c3.getColors().add(blue);
        sub2p5c3.getDimensions().add(shoe39);
        sub2p5c3.setParentProduct(p5c3);

        products.add(sub2p5c3);

        Product p6c3=new Product();
        p6c3.setDescription("OFFICIAL L. O. L. Surprise kid's smartwatch with selfie-cam, voice recorder, 3x games, pedometer, alarm, stopwatch, and calculator\n" +
                "FUN SELFIE-CAM AND VIDEO, download your awesome photos and videos - share these priceless moments with friends and family in a safe and secure way!");
        p6c3.setName("L.O.L. Surprise! Touch-Screen Smartwatch, Built in Selfie-Camera, Easy-to-Buckle Strap, Pink Smart Watch - Model: LOL4104");
        p6c3.setPrice(29.99);
        p6c3.setUnit(10);
        p6c3.setOrigin("china");
        p6c3.setColor("Pink");
        p6c3.getCategories().add(WatchesG);
        p6c3.getCategories().add(categoryRepository.findById(WatchesG.getParenetId()).get());
        p6c3.getCategories().add(Watches);
        p6c3.getCategories().add(categoryRepository.findById(Watches.getParenetId()).get());
        p6c3.setImage(BASE_URL+"user/image/p6c3.jpg");
        p6c3.getTags().add(new Tags("watch girls",p6c3));
        p6c3.getTags().add(new Tags("watch girl",p6c3));
        products.add(p6c3);

        Product p7c3=new Product();
        p7c3.setDescription("The Grant collection is always in style thanks to its time-honored design. This season we're updating it with modern roman numeral markers and materials like stainless steel and soft leathe . The result? A watch you'll wear for years to come.");
        p7c3.setName("Fossil Men's Grant Stainless Steel Chronograph Quartz Watch");
        p7c3.setPrice(129.00);
        p7c3.setUnit(10);
        p7c3.setOrigin("china");
        p7c3.setColor("Black");
        p6c3.getCategories().add(WatchesM);
        p6c3.getCategories().add(categoryRepository.findById(WatchesM.getParenetId()).get());
        p7c3.setImage(BASE_URL+"user/image/p7c3.jpg");
        p7c3.getTags().add(new Tags("watch Men's",p7c3));
        p7c3.getTags().add(new Tags("watch Mens",p7c3));
        products.add(p7c3);




        Product p6c2=new Product();
        p6c2.setDescription("Get high performance, responsiveness and long battery life with the Intel Core i7 -1165G7 Processor - up to 4.7GHz, 4 cores, 8 threads, 12MB Intel Smart Cache\n" +
                "14\" Full HD (1920 x 1080) IPS Widescreen LED-backlit 100% sRGB Display | Intel Iris Xe Graphics\n" +
                "8GB Onboard LPDDR4X Memory and 256GB NVMe SSD\n" +
                "1 - USB Type-C Port USB 3.2 Gen 2 (up to 10 Gbps) DisplayPort over USB Type-C, Thunderbolt 4 & USB Charging | 1 - USB 3.2 Gen 1 port (featuring power-off charging) | 1 - USB 2.0 port | 1 - HDMI port\n" +
                "Intel Wireless Wi-Fi 6 | Acer Bio-Protection Fingerprint Solution | Up to 16 Hours Battery Life");
        p6c2.setName("Acer Swift 3 Intel Evo Thin & Light Laptop, 14\" Full HD, Intel Core i7 -1165G7, Intel Iris Xe Graphics, 8GB LPDDR4X, 256GB NVMe SSD, Wi-Fi 6, Fingerprint Reader, Back-lit KB, SF314-59-75QC");
        p6c2.setPrice(781.45);
        p6c2.setUnit(10);
        p6c2.setOrigin("china");
        p6c2.getCategories().add(Laptop);
        p6c2.getCategories().add(categoryRepository.findById(Laptop.getParenetId()).get());
        p6c2.setImage(BASE_URL+"user/image/p6c2.jpg");
        p6c2.getTags().add(new Tags("acer",p6c2));
        p6c2.getTags().add(new Tags("laptop",p6c2));
        p6c2.getTags().add(new Tags("i7 Processor",p6c2));
        p6c2.getTags().add(new Tags("DDR4",p6c2));
        p6c2.getTags().add(new Tags("256 ssd",p6c2));
        products.add(p6c2);


        Product p7c2=new Product();
        p7c2.setDescription("9th Generation Intel Core i7 -9750H 6-Core Processor (Up to 4.5 GHz)\n" +
                "15.6\" Full HD Widescreen IPS LED-backlit display | 144 hertz refresh rate | NVIDIA GeForce RTX 2060 Graphics with 6 GB of dedicated GDDR6 VRAM\n" +
                "16GB DDR4 2666MHz Memory | 256GB PCIe NVMe SSD (2 x PCIe M.2 slots - 1 slot open for easy upgrades) & 1 - Available hard drive bay\n" +
                "LAN: 10/100/1000 Gigabit Ethernet LAN (RJ-45 port) | Wireless: Intel Wireless Wi-Fi 6 AX200 802.11ax\n" +
                "Backlit keyboard | Acer CoolBoost technology with twin fans and dual exhaust ports");
        p7c2.setName("Acer Nitro 5 Gaming Laptop, 9th Gen Intel Core i7 -9750H, NVIDIA GeForce RTX 2060, 15.6\" Full HD IPS 144Hz Display, 16GB DDR4, 256GB NVMe SSD, Wi-Fi 6, Waves MaxxAudio, Backlit Keyboard, AN515-54-728C");
        p7c2.setPrice(995.00);
        p7c2.setUnit(10);
        p7c2.setOrigin("china");
        p7c2.getCategories().add(Laptop);
        p7c2.getCategories().add(categoryRepository.findById(Laptop.getParenetId()).get());
        p7c2.setImage(BASE_URL+"user/image/p7c2.jpg");
        p7c2.getTags().add(new Tags("acer",p7c2));
        p7c2.getTags().add(new Tags("laptop",p7c2));
        p7c2.getTags().add(new Tags("i7 Processor",p7c2));
        p7c2.getTags().add(new Tags("DDR4",p7c2));
        p7c2.getTags().add(new Tags("RTX 2060",p7c2));
        products.add(p7c2);

        Product p8c2=new Product();
        p8c2.setDescription("Intel 1.7 GHz Core i5 -8350U Quad Core CPU\n" +
                "16GB DDR4 / 512GB SSD / Webcam\n" +
                "14\" FHD (1920 x 1080) Display / HDMI / USB-C / VGA\n" +
                "802.11ac / Bluetooth / AC Adapter Included\n" +
                "Genuine Windows 10 Pro Installed (64-bit)");
        p8c2.setName("Dell Latitude 5490 / Intel 1.7 GHz Core i5 -8350U Quad Core CPU / 16GB RAM / 512GB SSD / 14\" FHD (1920 x 1080) Display / HDMI / USB-C / Webcam / Windows 10 Pro (Renewed)");
        p8c2.setPrice(629.99);
        p8c2.setUnit(10);
        p8c2.setOrigin("china");
        p8c2.getCategories().add(Laptop);
        p8c2.getCategories().add(categoryRepository.findById(Laptop.getParenetId()).get());
        p8c2.setImage(BASE_URL+"user/image/p8c2.jpg");
        p8c2.getTags().add(new Tags("dell",p8c2));
        p8c2.getTags().add(new Tags("laptop",p8c2));
        p8c2.getTags().add(new Tags("i5 Processor",p8c2));
        p8c2.getTags().add(new Tags("DDR4",p8c2));
        p8c2.getTags().add(new Tags("512 SSD",p8c2));
        products.add(p8c2);

        Product p9c2=new Product();
        p9c2.setDescription("13.3\" Touchscreen InfinityEdge Full HD (1920 x 1080) Display, Intel UHD Graphics\n" +
                "10th Gen Intel Core i5 -10210U Processor, 8GB RAM, 256GB PCIe NVMe M.2 SSD\n" +
                "2x Thunderbolt 3 with PowerDelivery & Display Port, 1x USB-C 3.1, 1x MicroSD Card Reader\n" +
                "Backlit Keyboard, Fingerprint Reader, Stereo Speakers with MaxxAudio Pro\n" +
                "Windows 10 Home 64bit");
        p9c2.setName("Dell XPS7390 13\" InfinityEdge Touchscreen Laptop, Newest 10th Gen Intel i5 -10210U, 8GB RAM, 256GB SSD, Windows 10 Home");
        p9c2.setPrice(849.99);
        p9c2.setUnit(10);
        p9c2.setOrigin("china");
        p9c2.getCategories().add(Laptop);
        p9c2.getCategories().add(categoryRepository.findById(Laptop.getParenetId()).get());
        p9c2.setImage(BASE_URL+"user/image/p9c2.jpg");
        p9c2.getTags().add(new Tags("dell",p9c2));
        p9c2.getTags().add(new Tags("laptop",p9c2));
        p9c2.getTags().add(new Tags("i5 Processor",p9c2));
        p9c2.getTags().add(new Tags("DDR4",p9c2));
        p9c2.getTags().add(new Tags("256 SSD",p9c2));
        products.add(p9c2);

        Product p10c2=new Product();
        p10c2.setDescription("10th Gen Intel Core i7 -1065G7 Processor 1.3GHz\n" +
                "16GB 2666MHz DDR4 RAM\n" +
                "2.0TB SATA 5400 RPM Hard Drive + 256GB PCIe NVMe M.2 Solid State Drive\n" +
                "DVD-RW (Writes to DVD/CD)\n" +
                "17.3\" Anti-Glare LED-Backlit FHD (1920 x 1080) Display and 2GB NVIDIA GeForce MX230 Graphics");
        p10c2.setName("Dell Inspiron 17 17.3\" i3793-7275SLV-PUS 10th Gen Intel Core i7 -1065G7 16GB RAM 2TB HDD + 256GB SSD DVD-RW 2GB NVIDIA MX230 (1920 x 1080) Display Windows 10 Home");
        p10c2.setPrice(84.99);
        p10c2.setUnit(10);
        p10c2.setOrigin("china");
        p10c2.getCategories().add(Laptop);
        p10c2.getCategories().add(categoryRepository.findById(Laptop.getParenetId()).get());
        p10c2.setImage(BASE_URL+"user/image/p10c2.jpg");
        p10c2.getTags().add(new Tags("laptop",p10c2));
        p10c2.getTags().add(new Tags("dell",p10c2));
        p10c2.getTags().add(new Tags("i7 Processor",p10c2));
        p10c2.getTags().add(new Tags("DDR4",p10c2));
        p10c2.getTags().add(new Tags("256 SSD",p10c2));
        products.add(p10c2);
*/

        /*
        * Product p6c2=new Product();
        p6c2.setDescription("");
        p6c2.setName("");
        p6c2.setPrice(84.99);
        p6c2.setUnit(10);
        p6c2.setOrigin("china");
        p6c2.setImage(BASE_URL+"user/image/p6c2.jpg");
        products.add(p6c2);*/
        /*
        Product p3c1=new Product();
        p3c1.setDescription("");
        p3c1.setName("");
        p3c1.setPrice(84.99);
        p3c1.setUnit(10);
        p3c1.setOrigin("china");
        p3c1.getCategories().add(subCat2);
        products.add(p3c1);
        * */
        return products;
    }

}
