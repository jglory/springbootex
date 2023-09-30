package page.aaws.b01.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import page.aaws.b01.domain.Board;
import page.aaws.b01.domain.QBoard;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {
        QBoard board = QBoard.board;    // Q도메인 객체

        JPQLQuery<Board> query = from(board);   // select .. from board
        query.where(board.title.contains("2")); // where title like ..

        List<Board> list = query.fetch();
        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(QBoard.board);

        if ((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for (String type: types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }

        query.where(board.id.gt(0L));

        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<Board>(list, pageable, count);
    }
}
