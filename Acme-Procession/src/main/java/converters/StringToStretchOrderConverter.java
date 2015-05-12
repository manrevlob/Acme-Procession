package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.StretchOrderRepository;
import domain.StretchOrder;

@Component
@Transactional
public class StringToStretchOrderConverter implements Converter<String, StretchOrder> {

	@Autowired
	StretchOrderRepository stretchOrderRepository;

	@Override
	public StretchOrder convert(String text) {
		StretchOrder result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = stretchOrderRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
