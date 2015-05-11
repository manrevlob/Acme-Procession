package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.BoxInvoiceRepository;
import domain.BoxInvoice;

@Component
@Transactional
public class StringToBoxInvoiceConverter implements Converter<String, BoxInvoice> {

	@Autowired
	BoxInvoiceRepository boxInvoiceRepository;

	@Override
	public BoxInvoice convert(String text) {
		BoxInvoice result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = boxInvoiceRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}