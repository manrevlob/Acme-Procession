package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ViewerRepository;
import domain.Viewer;

@Component
@Transactional
public class StringToViewerConverter implements Converter<String, Viewer> {

	@Autowired
	ViewerRepository viewerRepository;

	@Override
	public Viewer convert(String text) {
		Viewer result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = viewerRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}