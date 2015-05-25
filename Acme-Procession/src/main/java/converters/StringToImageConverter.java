package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ImageRepository;
import domain.Image;

@Component
@Transactional
public class StringToImageConverter implements Converter<String, Image> {

	@Autowired
	ImageRepository imageRepository;

	@Override
	public Image convert(String text) {
		Image result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = imageRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}