/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SchoolCardTestModule } from '../../../test.module';
import { LostInfDetailComponent } from 'app/entities/lost-inf/lost-inf-detail.component';
import { LostInf } from 'app/shared/model/lost-inf.model';

describe('Component Tests', () => {
    describe('LostInf Management Detail Component', () => {
        let comp: LostInfDetailComponent;
        let fixture: ComponentFixture<LostInfDetailComponent>;
        const route = ({ data: of({ lostInf: new LostInf(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SchoolCardTestModule],
                declarations: [LostInfDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LostInfDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LostInfDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.lostInf).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
