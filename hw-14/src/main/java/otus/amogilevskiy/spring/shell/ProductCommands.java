package otus.amogilevskiy.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import otus.amogilevskiy.spring.domain.jpa.Product;
import otus.amogilevskiy.spring.repository.jpa.ProductRepository;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ProductCommands {

    private final ProductRepository productRepository;

    @ShellMethod(value = "Show all products from relational database.", key = {"products"})
    public String showProducts() {
        return productRepository.findAll().stream().map(Product::toString).collect(Collectors.joining("\n"));
    }

}
