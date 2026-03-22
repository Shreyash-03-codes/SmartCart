package com.smartcart.ecommerce.modules.cart.services.impl;

import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.common.exceptions.ProductDoesNotExistsException;
import com.smartcart.ecommerce.common.services.GetUserService;
import com.smartcart.ecommerce.modules.cart.dtos.GetCartItemDto;
import com.smartcart.ecommerce.modules.cart.model.CartItem;
import com.smartcart.ecommerce.modules.cart.repository.CartItemRepository;
import com.smartcart.ecommerce.modules.cart.services.CartService;
import com.smartcart.ecommerce.modules.product.model.Product;
import com.smartcart.ecommerce.modules.product.repository.ProductRepository;
import com.smartcart.ecommerce.modules.user.model.User;
import com.smartcart.ecommerce.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {


    private final CartItemRepository cartItemRepository;


    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final GetUserService getUserService;




    @Override
    public Page<GetCartItemDto> getCart(int pageIndex, int pageSize) {

        Pageable pageable=PageRequest.of(pageIndex,pageSize);
        User user= getUserService.getUser();
        Page<CartItem> cartItems=cartItemRepository.findAllByUser(user,pageable);

        return cartItems.map(c->{
            return GetCartItemDto.builder()
                    .cartItemId(c.getId())
                    .quantity(c.getQuantity())
                    .imagePaths(c.getProduct().getImages())
                    .productId(c.getProduct().getId())
                    .productName(c.getProduct().getName())
                    .productPrice(c.getProduct().getPrice())
                    .build();
        });
    }


    @Override
    @Transactional
    public GenericResponseDto addToCart(Long productId) {

        User user= getUserService.getUser();
        Product product=productRepository.findById(productId).orElseThrow(()->new ProductDoesNotExistsException("Product does not exists with id "+productId));
        Optional<CartItem> cartItemOptional=cartItemRepository.findByUserAndProduct(user,product);
        if(cartItemOptional.isPresent()){
            CartItem cartItem=cartItemOptional.get();
            cartItem.setQuantity(cartItem.getQuantity()+1);
            cartItemRepository.save(cartItem);
        }
        else {

            CartItem cartItem = CartItem.builder()
                    .quantity(1)
                    .product(product)
                    .user(user)
                    .build();
            cartItemRepository.save(cartItem);
        }



        return new GenericResponseDto("Item added successfully in Cart.");
    }

    @Override
    @Transactional
    public GenericResponseDto updateCount(Long cartItemId, int count, boolean add) {

        User user= getUserService.getUser();
        CartItem cartItem=cartItemRepository.findByIdAndUser(cartItemId,user).orElseThrow(()->new RuntimeException("Unauthorized Access"));

        if(add){
            cartItem.setQuantity(cartItem.getQuantity()+count);
        }else{
            int updatedQuantity=cartItem.getQuantity()-count;
            if(updatedQuantity<=0){
                cartItemRepository.delete(cartItem);
            }
            else{
                cartItem.setQuantity(updatedQuantity);
                cartItemRepository.save(cartItem);
            }
        }
        return new GenericResponseDto("Quantity"+(add?" Added":" removed" )+" successfully.");
    }

    @Override
    public GenericResponseDto removeCartItem(Long cartItemId) {
        User user= getUserService.getUser();
        CartItem cartItem=cartItemRepository.findByIdAndUser(cartItemId,user).orElseThrow(()->new RuntimeException("Unauthorized Access"));
        cartItemRepository.delete(cartItem);
        return new GenericResponseDto("CartItem removed successfully.");
    }



}
