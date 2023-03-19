import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Article} from "../../model/article";

const queryFindArticlesRss = gql`
  query findArticlesRss($keywords: [String!], $from: String, $to: String) {
    findArticlesRss(keywords: $keywords, from: $from, to: $to) {
      url,
      title,
      description,
      publishedAt
    }
  }
`;

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(private apollo: Apollo) {
  }

  public getArticlesRss(keywords: string[], from: Date | null, to: Date | null): Observable<Article[]> {
    return this.apollo.watchQuery<any>({
      query: queryFindArticlesRss,
      variables: {
        keywords: keywords,
        from: from,
        to: to
      }
    }).valueChanges.pipe(map(result => result.data.findArticlesRss));
  }

}
