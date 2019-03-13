package com.packtpub.reactiveendpoints.boundaries;

import com.packtpub.reactiveendpoints.domain.Comment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

@RestController
public class NotificationsController {

    @GetMapping(value = "/{singer}/comments", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Comment> querySingerComments(@PathVariable String singer) {
        // generate one flux element per second
        Flux<Long> intervalToGenerateComments = Flux.interval(Duration.ofSeconds(1));

        Flux<Comment> comments = Flux.fromStream(Stream.generate(() -> new Comment(composeComment(singer), new Date())));
        return Flux.zip(intervalToGenerateComments, comments)
                .map(fluxTuple -> fluxTuple.getT2());
    }

    private String composeComment(String singer) {
        String[] comments = new String[]{"Awesome", "Bad", "Great", "Cool", "Funky"};
        return singer + " is " + comments[new Random().nextInt(comments.length)];
    }

}
