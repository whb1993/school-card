/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SchoolCardTestModule } from '../../../test.module';
import { FillInfDetailComponent } from 'app/entities/fill-inf/fill-inf-detail.component';
import { FillInf } from 'app/shared/model/fill-inf.model';

describe('Component Tests', () => {
    describe('FillInf Management Detail Component', () => {
        let comp: FillInfDetailComponent;
        let fixture: ComponentFixture<FillInfDetailComponent>;
        const route = ({ data: of({ fillInf: new FillInf(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SchoolCardTestModule],
                declarations: [FillInfDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FillInfDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FillInfDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.fillInf).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
