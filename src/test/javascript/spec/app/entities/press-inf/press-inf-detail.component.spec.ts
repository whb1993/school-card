/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SchoolCardTestModule } from '../../../test.module';
import { PressInfDetailComponent } from 'app/entities/press-inf/press-inf-detail.component';
import { PressInf } from 'app/shared/model/press-inf.model';

describe('Component Tests', () => {
    describe('PressInf Management Detail Component', () => {
        let comp: PressInfDetailComponent;
        let fixture: ComponentFixture<PressInfDetailComponent>;
        const route = ({ data: of({ pressInf: new PressInf(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SchoolCardTestModule],
                declarations: [PressInfDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PressInfDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PressInfDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pressInf).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
