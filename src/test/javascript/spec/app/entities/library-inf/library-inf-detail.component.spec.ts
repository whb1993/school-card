/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SchoolCardTestModule } from '../../../test.module';
import { LibraryInfDetailComponent } from 'app/entities/library-inf/library-inf-detail.component';
import { LibraryInf } from 'app/shared/model/library-inf.model';

describe('Component Tests', () => {
    describe('LibraryInf Management Detail Component', () => {
        let comp: LibraryInfDetailComponent;
        let fixture: ComponentFixture<LibraryInfDetailComponent>;
        const route = ({ data: of({ libraryInf: new LibraryInf(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SchoolCardTestModule],
                declarations: [LibraryInfDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LibraryInfDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LibraryInfDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.libraryInf).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
