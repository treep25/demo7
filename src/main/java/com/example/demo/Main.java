package com.example.demo;

import com.example.demo.tag.model.Tag;

import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Set<Tag> tags = Set.of(new Tag().builder().name("qwe").build(),new Tag().builder().name("rty").build());
        System.out.println( ASD.df(tags).toString());
    }

}
class ASD{
    public static Set<?> df(Set<Tag> tags){
         tags.forEach(x1->x1.setId(123L));
        return tags;
    }
}
