package com.invillia.acme;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

import com.invillia.acme.messaging.PaymentsChannels;

/**
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
@Configuration
@EnableBinding(PaymentsChannels.class)
public class Config {

}
